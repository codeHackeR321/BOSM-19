package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData
import io.reactivex.schedulers.Schedulers

class OrdersViewModel(val walletRepository: WalletRepository): ViewModel(){

    var orders: LiveData<List<ModifiedOrdersData>> = MutableLiveData()

    init {

        walletRepository.getAllOrders()
            .doOnNext {
                Log.d("OrdersVM",it.toString())
                (orders as MutableLiveData).postValue(it)
            }
            .doOnError {
                Log.e("OrdersVM","Error",it)
            }
            .subscribe()

    }

    fun updateOtpSeen(orderId: Int){
        walletRepository.updateOtpSeen(orderId)
    }
}