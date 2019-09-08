package com.dvm.appd.bosm.dbg.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedComboData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ShowsTickets
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.TicketsCart

class TicketViewModel(val walletRepository: WalletRepository): ViewModel(){

    var comboTickets: LiveData<List<ModifiedComboData>> = MutableLiveData()
    var showTickets: LiveData<List<ShowsTickets>> = MutableLiveData()

    init {

        walletRepository.getAllShowsData()
            .doOnNext {
                Log.d("Tickets", "$it")
                (showTickets as MutableLiveData).postValue(it)
            }
            .subscribe()

        walletRepository.getAllComboData()
            .doOnNext {
                Log.d("Tickets", "$it")
                (comboTickets as MutableLiveData).postValue(it)
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