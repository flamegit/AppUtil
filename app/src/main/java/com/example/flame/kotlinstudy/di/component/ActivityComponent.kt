package com.example.flame.kotlinstudy.di.component

import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.ui.DataBindingActivity
import com.example.flame.kotlinstudy.ui.HomeActivity
import com.example.flame.kotlinstudy.ui.LiveDataActivity
import dagger.Subcomponent

/**
 * Created by flame on 2018/2/1.
 */

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: HomeActivity)

    fun inject(activity: LiveDataActivity)

    fun inject(activity: DataBindingActivity)


}