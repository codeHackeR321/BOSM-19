package com.dvm.appd.bosm.dbg.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.di.profile.ProfileModule
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TicketViewModelFactory: ViewModelProvider.Factory{

    @Inject
    lateinit var walletRepository: WalletRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newProfileComponent(ProfileModule()).injectTickets(this)
        return TicketViewModel(walletRepository) as T
    }

}