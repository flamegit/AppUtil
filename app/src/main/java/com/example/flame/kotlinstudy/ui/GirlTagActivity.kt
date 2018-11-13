package com.example.flame.kotlinstudy.ui

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Tag
import com.example.flame.kotlinstudy.utils.createGlideUrl
import com.example.flame.kotlinstudy.utils.openActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_girl_tag.*
import javax.inject.Inject

class GirlTagActivity : AppCompatActivity() {

    @Inject
    @JvmField
    var parser: AbstractParser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl_tag)
        val url = intent.getStringExtra(Constants.KEY_URL)
        val app = application as App
        app.component.plus(ActivityModule(this, url)).inject(this)

        val adapter = CommonAdapter<Tag>(R.layout.viewholder_tag_girl) { holder, _, data ->
            Glide.with(this).load(createGlideUrl(data.cover)).into(holder[R.id.image_view])
            holder.get<TextView>(R.id.desc_view).text = data.name
            holder.itemView.setOnClickListener {
                this@GirlTagActivity.openActivity(GirlListActivity::class.java,Constants.KEY_URL,data.url)
            }
        }
        tag_recycler_view.apply {
            this.adapter = adapter
            layoutManager= GridLayoutManager(context,4)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect?.set(5, 5, 5, 5)
                }
            })
        }
        val disposable = Observable.fromCallable {
            parser?.getTagList()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.addItems(it,false)
                },{
                    it.printStackTrace()
                })
    }
}
