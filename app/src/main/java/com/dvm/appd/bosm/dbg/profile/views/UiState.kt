package com.dvm.appd.bosm.dbg.profile.views

import com.dvm.appd.bosm.dbg.auth.data.User

sealed class UiState {

    object MoveToLogin:UiState()
    data class ShowProfile(val user: User):UiState()
    object Loading:UiState()
}