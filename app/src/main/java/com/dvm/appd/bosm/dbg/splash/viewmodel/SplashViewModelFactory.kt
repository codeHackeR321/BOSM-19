package com.dvm.appd.bosm.dbg.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.di.splash.SplashModule
import javax.inject.Inject

class SplashViewModelFactory:ViewModelProvider.Factory {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newSplashComponent(SplashModule()).inject(this)
        return SplashViewModel(authRepository) as T
    }
}