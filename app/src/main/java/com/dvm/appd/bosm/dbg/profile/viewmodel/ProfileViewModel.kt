package com.dvm.appd.bosm.dbg.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.auth.data.User
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.auth.data.retrofit.AuthPojo
import com.dvm.appd.bosm.dbg.profile.views.UiState
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import java.lang.IllegalStateException

class ProfileViewModel(val authRepository: AuthRepository,val walletRepository: WalletRepository) :ViewModel() {

    var order: LiveData<UiState> = MutableLiveData()
    var user:LiveData<User> = MutableLiveData()
    init {
          authRepository.getUser().subscribe({
              (user as MutableLiveData).postValue(it!!)
          },{
              Log.d("check",it.toString())
          })
    }

    fun logout(){
        (order as MutableLiveData).postValue(UiState.ShowLoading)
        authRepository.setUser(null).subscribe({
            (order as MutableLiveData).postValue(UiState.MoveToLogin)
        },{
            Log.d("checkl",it.toString())
        })
    }


   fun transferMoney(){
       
   }
}