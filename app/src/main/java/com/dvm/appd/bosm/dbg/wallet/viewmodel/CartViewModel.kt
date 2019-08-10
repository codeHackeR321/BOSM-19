package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.CartData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartData

class CartViewModel(val walletRepository: WalletRepository): ViewModel(){

    var cartItems: LiveData<Pair<List<ModifiedCartData>, Int>> = MutableLiveData()

    init {

        walletRepository.getAllModifiedCartItems()
            .doOnNext {
                Log.d("CartVM", it.toString())
                (cartItems as MutableLiveData).postValue(it)
            }
            .subscribe()
    }

    fun placeOrder(){
        walletRepository.placeOrder().subscribe()
    }

    fun deleteCartItem(itemId: Int){
        walletRepository.deleteCartItem(itemId).subscribe()
    }

    fun updateCartItems(itemId: Int, quantity: Int){
        walletRepository.updateCartItems(itemId, quantity).subscribe()
    }
}