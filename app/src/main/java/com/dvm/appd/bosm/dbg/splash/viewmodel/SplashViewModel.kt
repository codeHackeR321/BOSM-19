package com.dvm.appd.bosm.dbg.splash.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.splash.views.UiState

class SplashViewModel(val authRepository: AuthRepository):ViewModel() {

    var state: LiveData<UiState> = MutableLiveData()

    init {
        authRepository.getUser().subscribe({
            (state as MutableLiveData).postValue(UiState.GoToMainApp)
        },{
            Log.d("check",it.toString())
        },{
            (state as MutableLiveData).postValue(UiState.Login)
        })
    }
}