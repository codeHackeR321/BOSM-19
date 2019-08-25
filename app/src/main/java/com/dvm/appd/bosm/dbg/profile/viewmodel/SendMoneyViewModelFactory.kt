package com.dvm.appd.bosm.dbg.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.di.profile.ProfileModule

class SendMoneyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newProfileComponent(ProfileModule()).inject(this)
        return SendMoneyViewModel() as T
    }
}