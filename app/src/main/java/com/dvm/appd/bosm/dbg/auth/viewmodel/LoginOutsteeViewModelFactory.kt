package com.dvm.appd.bosm.dbg.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.di.auth.AuthModule
import javax.inject.Inject

class LoginOutsteeViewModelFactory:ViewModelProvider.Factory {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newAuthComponent(AuthModule()).inject(this)

        return LoginOutsteeViewModel(authRepository) as T
    }
}