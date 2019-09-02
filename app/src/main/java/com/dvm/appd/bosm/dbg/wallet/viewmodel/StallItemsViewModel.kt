package com.dvm.appd.bosm.dbg.wallet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.CartData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedStallItemsData

class StallItemsViewModel(val walletRepository: WalletRepository, val stallId: Int) : ViewModel() {

    var items:LiveData<List<ModifiedStallItemsData>> = MutableLiveData()
    var cartItems: LiveData<List<ModifiedCartData>> = MutableLiveData()

    init {


        walletRepository.getItemsForStall(stallId)
            .subscribe({
                Log.d("StallItemVM", it.toString())
                (items as MutableLiveData).postValue(it)
            }
                , {
                    Log.d("checkve", it.toString())
                }
            )

        walletRepository.getAllModifiedCartItems()
            .doOnNext {
                Log.d("CartVM", it.toString())
                (cartItems as MutableLiveData).postValue(it)
            }
            .subscribe()

    }

    fun deleteCartItem(itemId: Int) {
        walletRepository.deleteCartItem(itemId).subscribe()
    }

    fun insertCartItems(cartData: CartData) {
        walletRepository.insertCartItems(cartData).subscribe()
    }
}