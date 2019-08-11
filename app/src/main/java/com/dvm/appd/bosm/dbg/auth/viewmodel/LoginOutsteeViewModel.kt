package com.dvm.appd.bosm.dbg.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository

class LoginOutsteeViewModel(val authRepository: AuthRepository):ViewModel() {

    fun login(username:String,password:String){

        authRepository.loginOutstee(username,password)

    }
}