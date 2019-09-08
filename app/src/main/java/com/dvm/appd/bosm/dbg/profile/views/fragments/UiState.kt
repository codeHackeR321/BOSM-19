package com.dvm.appd.bosm.dbg.profile.views.fragments

sealed class UiState {
    object MoveToLogin : UiState()
    object ShowLoading: UiState()
    object ShowIdle: UiState()
}