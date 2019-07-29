package com.dvm.appd.bosm.dbg.wallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.di.wallet.WalletModule
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import javax.inject.Inject

class StallItemsViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var walletRepository: WalletRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newWalletComponent(WalletModule()).inject(this)
        return StallItemsViewModel(walletRepository) as T
    }
}