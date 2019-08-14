package com.dvm.appd.bosm.dbg.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.profile.views.UiState

class ProfileViewModel(val authRepository: AuthRepository) :ViewModel() {

    val state:LiveData<UiState> = MutableLiveData()

    init {
        (state as MutableLiveData).value = UiState.Loading
        checkUser()
    }

    fun checkUser(){
        authRepository.getUser().subscribe({
            (state as MutableLiveData).postValue(UiState.ShowProfile(it))
        },{
            Log.d("checke",it.toString())
        },{
            (state as MutableLiveData).postValue(UiState.MoveToLogin)
        })
    }
}