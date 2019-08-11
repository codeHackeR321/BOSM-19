package com.dvm.appd.bosm.dbg.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.auth.views.LoginState

class LoginOutsteeViewModel(val authRepository: AuthRepository) : ViewModel() {

    val state: LiveData<LoginState> = MutableLiveData()

    fun login(username: String, password: String) {

        authRepository.loginOutstee(username, password).doOnSuccess {
            (state as MutableLiveData).postValue(it!!)
        }.doOnError {
            Log.d("checkve", it.toString())
        }.subscribe()

    }
}