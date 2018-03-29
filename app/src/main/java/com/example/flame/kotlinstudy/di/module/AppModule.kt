package com.example.flame.kotlinstudy.di.module

import android.app.Application
import com.example.flame.kotlinstudy.datasource.net.ApiService
import com.example.flame.kotlinstudy.datasource.net.NetManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by flame on 2018/2/1.
 */
@Module
class AppModule(private val app:Application){

    @Singleton
    @Provides
    fun provideApp()=app


    @Singleton
    @Provides
    fun provideApi():ApiService{
       return NetManager.getRetrofit("http://gank.io/api/").create(ApiService::class.java)
    }

}
