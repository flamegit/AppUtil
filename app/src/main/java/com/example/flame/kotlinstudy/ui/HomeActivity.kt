package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.presenter.GirlPresenter
import com.example.flame.kotlinstudy.view.CommonView
import javax.inject.Inject

@ActivityScope
class HomeActivity : AppCompatActivity(),CommonView<List<Girl>> {

    @Inject lateinit var mPresenter:GirlPresenter
    @Inject lateinit var mAdapter:CommonAdapter<Girl>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app= application as App
        app.component.plus(ActivityModule(this)).inject(this)

        setContentView(R.layout.activity_detail)
        mPresenter.attachView(this)
        mPresenter.load()
        findViewById<RecyclerView>(R.id.recycler_view).adapter=mAdapter

//        findViewById<View>(R.id.button).setOnClickListener {
//            startActivity(Intent(HomeActivity@this,DetailActivity::class.java))
//        }
    }

    override fun success(data: List<Girl>?) {
        mAdapter.addItems(data,false)
    }

    override fun failed(code: Int) {
    }
}
