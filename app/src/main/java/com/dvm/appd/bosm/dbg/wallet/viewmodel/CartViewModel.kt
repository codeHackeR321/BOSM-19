package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartData

class CartViewModel(val walletRepository: WalletRepository): ViewModel(){

    var cartItems: LiveData<List<ModifiedCartData>> = MutableLiveData()
    var progressBarMark: LiveData<Int> = MutableLiveData(1)
    var error: LiveData<String> = MutableLiveData(null)

    init {

        walletRepository.getAllModifiedCartItems()
            .doOnNext {
                Log.d("CartVM", it.toString())
                (cartItems as MutableLiveData).postValue(it)
            }
            .subscribe()
    }

    @SuppressLint("CheckResult")
    fun placeOrder(){
        walletRepository.placeOrder().subscribe({
            (progressBarMark as MutableLiveData).postValue(1)
            (error as MutableLiveData).postValue("Order successful")
        },{
                (progressBarMark as MutableLiveData).postValue(1)
                (error as MutableLiveData).postValue(it.message)
        })
    }

    fun deleteCartItem(itemId: Int){
        walletRepository.deleteCartItem(itemId).subscribe()
    }

    fun updateCartItems(itemId: Int, quantity: Int){
        walletRepository.updateCartItems(itemId, quantity).subscribe()
    }
}