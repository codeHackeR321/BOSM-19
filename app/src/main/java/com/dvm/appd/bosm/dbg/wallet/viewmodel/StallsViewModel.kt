package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import io.reactivex.android.schedulers.AndroidSchedulers

class StallsViewModel(val walletRepository: WalletRepository):ViewModel() {

    var stalls:LiveData<List<StallData>> = MutableLiveData()

  init{
      walletRepository.fetchAllStalls().observeOn(AndroidSchedulers.mainThread()).doOnComplete {
          walletRepository.getAllStalls().subscribe(
              {
                  Log.d("check",it.toString())
                  (stalls as MutableLiveData).postValue(it)
              },{
                  Log.d("check",it.message)
              }
          )
      }.subscribe()
  }

}