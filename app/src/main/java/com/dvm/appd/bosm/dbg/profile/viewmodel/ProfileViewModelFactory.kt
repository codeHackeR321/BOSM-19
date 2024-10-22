package com.dvm.appd.bosm.dbg.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.di.profile.ProfileModule
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import javax.inject.Inject


class ProfileViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var walletRepository: WalletRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newProfileComponent(ProfileModule()).inject(this)
        return ProfileViewModel(authRepository,walletRepository) as T
    }
}