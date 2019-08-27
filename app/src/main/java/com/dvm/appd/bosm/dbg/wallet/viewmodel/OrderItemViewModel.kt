package com.dvm.appd.bosm.dbg.wallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData

class OrderItemViewModel(val walletRepository: WalletRepository, val orderId: Int): ViewModel(){

    val order: LiveData<ModifiedOrdersData> = MutableLiveData()

    init {

        walletRepository.getOrderById(orderId)
            .doOnNext{
                (order as MutableLiveData).postValue(it)
            }
            .doOnError {

            }
            .subscribe()
    }
}