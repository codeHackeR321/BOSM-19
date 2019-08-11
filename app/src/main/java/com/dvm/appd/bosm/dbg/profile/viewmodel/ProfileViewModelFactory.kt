package com.dvm.appd.bosm.dbg.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.di.profile.ProfileModule
import javax.inject.Inject


class ProfileViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newProfileComponent(ProfileModule()).inject(this)
        return ProfileViewModel(authRepository) as T
    }
}