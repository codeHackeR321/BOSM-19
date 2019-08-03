package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.CartData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartData
import io.reactivex.schedulers.Schedulers

class CartViewModel(val walletRepository: WalletRepository): ViewModel(){

    var modifiedCartItems: LiveData<List<ModifiedCartData>> = MutableLiveData()

    init {

        walletRepository.getAllModifiedCartItems()
            .doOnNext {
                Log.d("CartVM", it.toString())
                (modifiedCartItems as MutableLiveData).postValue(it)
            }
            .subscribe()
    }

    fun placeOrder(){
        walletRepository.placeOrder()
    }

    fun deleteCartItem(itemId: Int){
        walletRepository.deleteCartItem(itemId).subscribe()
    }

    fun updateQuantity(quantity: Int, itemId: Int){
        walletRepository.updateQuantity(quantity, itemId).subscribe()
    }

    fun insertCartItems(cartData: CartData){
        walletRepository.insertCartItems(cartData).subscribe()
    }
}