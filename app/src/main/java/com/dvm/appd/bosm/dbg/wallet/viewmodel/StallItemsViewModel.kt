package com.dvm.appd.bosm.dbg.wallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData

class StallItemsViewModel(val walletRepository: WalletRepository):ViewModel() {

    var items:LiveData<List<StallItemsData>> = MutableLiveData()
    init {

    }
}