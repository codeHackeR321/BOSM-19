package com.dvm.appd.bosm.dbg.di.splash

import com.dvm.appd.bosm.dbg.splash.viewmodel.SplashViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {

    fun inject(factory:SplashViewModelFactory)
}