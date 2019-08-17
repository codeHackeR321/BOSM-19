package com.dvm.appd.bosm.dbg.splash.views

sealed class UiState {

    object Login : UiState()
    object GoToMainApp : UiState()
}