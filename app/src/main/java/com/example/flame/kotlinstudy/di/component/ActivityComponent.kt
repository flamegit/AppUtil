package com.example.flame.kotlinstudy.di.component

import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.ui.*
import dagger.Subcomponent

/**
 * Created by flame on 2018/2/1.
 */

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: HomeActivity)

    fun inject(activity: LiveDataActivity)

    fun inject(activity: DataBindingActivity)

    fun inject(activity: ContentActivity)

    fun inject(activity: GirlOverViewActivity)

    fun inject(activity: GirlTagActivity)

    fun inject(activity: OperatorsStudyActivity)



}