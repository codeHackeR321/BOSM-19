package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData

class OrderItemViewModel(val walletRepository: WalletRepository, val orderId: Int): ViewModel() {

    val order: LiveData<ModifiedOrdersData> = MutableLiveData()

    init {

        walletRepository.getOrderById(orderId)
            .doOnNext {
                Log.d("OrderDetailVM", it.toString())
                (order as MutableLiveData).postValue(it)
            }
            .doOnError {

            }
            .subscribe()

    }

    fun rateOrder(shell: Int, rating: Int) {
        walletRepository.rateOrder(orderId, shell, rating)
            .doOnComplete {
                Log.d("Rated", "Rated")
            }
            .subscribe()
    }

    fun updateOtpSeen(orderId: Int){
        walletRepository.updateOtpSeen(orderId).subscribe()
    }
}