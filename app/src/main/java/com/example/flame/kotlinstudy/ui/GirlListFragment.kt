package com.example.flame.kotlinstudy.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.datasource.database.CategoryDao
import com.example.flame.kotlinstudy.di.module.FragmentModule
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.lib.SpaceItemDecoration
import com.example.flame.kotlinstudy.model.Category
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.utils.createGlideUrl
import com.example.flame.kotlinstudy.utils.dpToPx
import com.example.flame.kotlinstudy.utils.loadImage
import com.example.flame.kotlinstudy.utils.openActivity
import com.example.flame.kotlinstudy.viewmodel.CategoryViewModelFactory
import com.example.flame.kotlinstudy.viewmodel.CategoryViewModel
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_girl_list.*
import javax.inject.Inject

class GirlListFragment : Fragment() {

    @Inject
    lateinit var mViewModelFactory: CategoryViewModelFactory

    @Inject
    lateinit var categoryDao: CategoryDao

    var siteType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val url = it.getString(Constants.KEY_URL)
            siteType = it.getInt(Constants.KEY_SITE_TYPE)
            App.instance().component.plus(FragmentModule(siteType, url)).inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_girl_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh_layout.isEnabled = false
        val viewModel = ViewModelProviders.of(this, mViewModelFactory).get(CategoryViewModel::class.java)
        val adapter = CommonAdapter<Category>(R.layout.viewholder_girl) { holder, _, data ->
            context?.loadImage(data.cover,holder[R.id.image_view])
            holder.get<TextView>(R.id.desc_view).text = data.desc
            val favoriteView = holder.get<ImageView>(R.id.favorite_view)
            favoriteView.setImageResource(if (data.isFavorite) {
                R.drawable.favorite_press
            } else {
                R.drawable.favorite
            })
            favoriteView.setOnClickListener {
                if (!data.isFavorite) {
                    data.isFavorite = true
                    favoriteView.setImageResource(R.drawable.favorite_press)
                    Maybe.fromAction<Unit> {
                        categoryDao.insertCategory(data)
                    }.subscribeOn(Schedulers.io()).subscribe()
                }
            }
            holder.itemView.setOnClickListener {
                context?.openActivity(GirlOverViewActivity::class.java, Constants.KEY_SITE_TYPE, siteType, Constants.KEY_URL, data.url)
            }
        }
        viewModel.content.observe(this, Observer { data ->
            adapter.addItems(data, true)
            refresh_layout.isRefreshing = false
        })
        girl_list_view.apply {
            this.adapter = adapter
            addItemDecoration(SpaceItemDecoration(context.dpToPx(32)))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    recyclerView?.let {
                        if (!it.canScrollVertically(1) && !viewModel.loading) {
                            refresh_layout.isRefreshing = true
                            viewModel.loadMore()
                        }
                    }
                }
            })
            viewModel.load()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String, siteType: Int) =
                GirlListFragment().apply {
                    arguments = Bundle().apply {
                        putString(Constants.KEY_URL, url)
                        putInt(Constants.KEY_SITE_TYPE, siteType)
                    }
                }
    }
}
