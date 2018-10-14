package com.example.flame.kotlinstudy.di.component

import com.example.flame.kotlinstudy.di.module.FragmentModule
import com.example.flame.kotlinstudy.di.scope.FragmentScope
import com.example.flame.kotlinstudy.ui.GirlListFragment
import dagger.Subcomponent

/**
 * Created by flame on 2018/2/1.
 */

@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: GirlListFragment)

}