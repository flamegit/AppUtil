package com.example.flame.kotlinstudy

import android.app.Application
import com.example.flame.kotlinstudy.di.component.AppComponent
import com.example.flame.kotlinstudy.di.component.DaggerAppComponent
import com.example.flame.kotlinstudy.di.module.AppModule

/**
 * Created by flame on 2018/2/17.
 */
class App : Application() {

    lateinit var mComponent: AppComponent

    override fun onCreate() {

        super.onCreate()
         mComponent=DaggerAppComponent.builder().appModule(AppModule(this)).build()

        //toast(""+android.os.Process.myPid())
    }
}