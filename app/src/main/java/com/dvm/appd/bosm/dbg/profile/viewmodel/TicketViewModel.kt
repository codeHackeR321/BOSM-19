package com.dvm.appd.bosm.dbg.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedTicketsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.TicketsCart
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.TicketsData

class TicketViewModel(val walletRepository: WalletRepository): ViewModel(){

    var tickets: LiveData<List<ModifiedTicketsData>> = MutableLiveData()
    var error: LiveData<String> = MutableLiveData(null)

    init {

        walletRepository.getAllTicketData().subscribe({
            Log.d("TicketsShows", "$it")
            (tickets as MutableLiveData).postValue(it)
            (error as MutableLiveData).postValue(null)
        }, {
            (error as MutableLiveData).postValue(it.message)
        })

    }

    fun buyTickets(){
        walletRepository.buyTickets().subscribe({
            Log.d("Wallet Repo", "Entered success")
            (error as MutableLiveData).postValue(null)
        },{
            Log.d("Wallet Repo", "Errror in Api call = ${it.toString()}")
            (error as MutableLiveData).postValue(it.message)
        })
    }

    fun insertTicketCart(ticket: TicketsCart){
        walletRepository.insertTicketsCart(ticket).subscribe({
            (error as MutableLiveData).postValue(null)
        },{
            (error as MutableLiveData).postValue(it.message)
        })
    }

    fun deleteTiceketCartItem(id: Int){
        walletRepository.deleteTicektsCartItem(id).subscribe({
            (error as MutableLiveData).postValue(null)
        },{
            (error as MutableLiveData).postValue(it.message)
        })
    }

    fun updateTicketCart(quantity: Int, id: Int){
        walletRepository.updateTicketsCart(quantity, id).subscribe({
            (error as MutableLiveData).postValue(null)
        },{
            (error as MutableLiveData).postValue(it.message)
        })
    }
}