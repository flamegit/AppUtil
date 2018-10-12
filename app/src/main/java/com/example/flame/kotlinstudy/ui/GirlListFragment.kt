package com.example.flame.kotlinstudy.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.module.FragmentModule
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.model.Category
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.utils.createGlideUrl
import com.example.flame.kotlinstudy.utils.openActivity
import com.example.flame.kotlinstudy.viewmodel.CategoryViewModelFactory
import com.example.flame.kotlinstudy.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_girl_list.*
import javax.inject.Inject

class GirlListFragment : Fragment() {

    @Inject
    lateinit var mViewModelFactory: CategoryViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val url = it.getString(Constants.KEY_URL)
            App.instance().component.plus(FragmentModule(url)).inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_girl_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, mViewModelFactory).get(CategoryViewModel::class.java)
        val adapter = CommonAdapter<Category>(R.layout.viewholder_girl) { holder, _, data ->
            Glide.with(this).load(createGlideUrl(data.cover)).into(holder[R.id.image_view])
            holder.get<TextView>(R.id.desc_view).text = data.desc
            holder.itemView.setOnClickListener{
                context?.openActivity(GirlOverViewActivity::class.java,Constants.KEY_URL,data.url)
            }
        }
        viewModel.content.observe(this, Observer { data ->
            adapter.addItems(data, false)
        })
        girl_list_view.adapter = adapter
        viewModel.load()
    }



    companion object {
        @JvmStatic
        fun newInstance(url: String) =
                GirlListFragment().apply {
                    arguments = Bundle().apply {
                        putString(Constants.KEY_URL, url)
                    }
                }
    }
}
