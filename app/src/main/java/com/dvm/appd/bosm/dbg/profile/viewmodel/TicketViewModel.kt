package com.dvm.appd.bosm.dbg.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.TicketsCart
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.TicketsData

class TicketViewModel(val walletRepository: WalletRepository): ViewModel(){

    var tickets: LiveData<List<TicketsData>> = MutableLiveData()

    init {

        walletRepository.getAllTicketData()
            .doOnNext {
                Log.d("TicketsShows", "$it")
                (tickets as MutableLiveData).postValue(it)
            }
            .subscribe()

    }

    fun buyTickets(){
        walletRepository.buyTickets().subscribe()
    }

    fun insertTicketCart(ticket: TicketsCart){
        walletRepository.insertTicketsCart(ticket).subscribe()
    }

    fun deleteTiceketCartItem(id: Int){
        walletRepository.deleteTicektsCartItem(id).subscribe()
    }

    fun updateTicketCart(quantity: Int, id: Int){
        walletRepository.updateTicketsCart(quantity, id).subscribe()
    }
}