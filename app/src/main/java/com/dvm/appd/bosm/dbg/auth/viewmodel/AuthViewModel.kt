package com.dvm.appd.bosm.dbg.auth.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.auth.views.LoginState

class AuthViewModel(val authRepository: AuthRepository):ViewModel() {

    var state:LiveData<LoginState> = MutableLiveData()

    init {

    }

    @SuppressLint("CheckResult")
    fun login(id:String){

     authRepository.loginBitsian(id).subscribe({
         (state as MutableLiveData).postValue(it)
     },{
         Log.d("checke",it.toString())
         (state as MutableLiveData).postValue(LoginState.Failure(it.message.toString()))
     })
    }
}