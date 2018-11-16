package com.example.flame.kotlinstudy
import android.app.Application
import com.example.flame.kotlinstudy.di.component.AppComponent
import com.example.flame.kotlinstudy.di.component.DaggerAppComponent
import com.example.flame.kotlinstudy.di.module.AppModule
import com.tencent.stat.StatConfig
import com.tencent.stat.StatService

/**
 * Created by flame on 2018/2/17.
 */
class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        instance=this
        super.onCreate()
        component=DaggerAppComponent.builder().appModule(AppModule(this)).build()

        StatConfig.setDebugEnable(true)
        // 基础统计API
        StatService.registerActivityLifecycleCallbacks(this)
    }

    companion object {
        private var instance: App? = null
        fun instance() = instance?:throw Throwable("instance 还未初始化")
    }

}