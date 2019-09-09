package com.dvm.appd.bosm.dbg.auth.views

sealed class LoginState {
    object Success:LoginState()
    data class Failure(val message:String):LoginState()
    object MoveToOnBoarding:LoginState()
    object MoveToMainApp:LoginState()
    object Idle:LoginState()
    object Loading:LoginState()
}