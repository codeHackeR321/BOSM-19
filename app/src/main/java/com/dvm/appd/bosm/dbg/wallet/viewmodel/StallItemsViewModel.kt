package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData

class StallItemsViewModel(val walletRepository: WalletRepository,val stallId:Int):ViewModel() {

    var items:LiveData<List<StallItemsData>> = MutableLiveData()

    init {

         walletRepository.getItemsForStall(stallId).subscribe({
             (items as MutableLiveData).postValue(it)
         },{
             Log.d("checkve",it.toString())
         })
    }
}