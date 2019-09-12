package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import com.dvm.appd.bosm.dbg.wallet.views.StallResult
import io.reactivex.android.schedulers.AndroidSchedulers

class StallsViewModel(val walletRepository: WalletRepository):ViewModel() {

    var stalls:LiveData<List<StallData>> = MutableLiveData()
    var result: LiveData<StallResult> = MutableLiveData()
    var error: LiveData<String> = MutableLiveData(null)

  init{
      getAllStallData()
  }

    fun getAllStallData(){
        (result as MutableLiveData).postValue(StallResult.Failure)

        walletRepository.getAllStalls().subscribe({
            Log.d("check", it.toString())
            (stalls as MutableLiveData).postValue(it)
            (result as MutableLiveData).postValue(StallResult.Success)
        },{
            Log.d("check", it.message)
            (error as MutableLiveData).postValue(it.message)
        })
    }

}