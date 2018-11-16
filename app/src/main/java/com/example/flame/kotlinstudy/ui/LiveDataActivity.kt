package com.example.flame.kotlinstudy.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.lib.GIRL_TYPE
import com.example.flame.kotlinstudy.lib.MultiTypeAdapter
import com.example.flame.kotlinstudy.viewmodel.CommonViewModelFactory
import com.example.flame.kotlinstudy.viewmodel.LiveDataGirlViewModel
import javax.inject.Inject

@ActivityScope
class LiveDataActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewModelFactory: CommonViewModelFactory
    lateinit var mAdapter: MultiTypeAdapter
    lateinit var mRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        app.component.plus(ActivityModule(this)).inject(this)
        setContentView(R.layout.activity_detail)
        mAdapter = MultiTypeAdapter()
        mRefreshLayout = findViewById(R.id.refresh_layout)
        val viewModel = ViewModelProviders.of(this, mViewModelFactory).get(LiveDataGirlViewModel::class.java)

        findViewById<RecyclerView>(R.id.recycler_view).apply {
            this.layoutManager = GridLayoutManager(context, 2)
            this.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect?.set(5, 5, 5, 5)
                }
            })
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (recyclerView?.layoutManager is LinearLayoutManager) {
                        if (!recyclerView.canScrollVertically(1)) {
                            mRefreshLayout.isRefreshing = true
                            viewModel.loadMore()
                        }
                    }
                }
            })
        }

        viewModel.mData.observe(this, Observer { t ->
            mRefreshLayout.isRefreshing = false
            mAdapter.addItems(t, type = GIRL_TYPE, append = viewModel.mPager != 1)
        }
        )
        viewModel.load()
        mRefreshLayout.isRefreshing =true
    }
}
