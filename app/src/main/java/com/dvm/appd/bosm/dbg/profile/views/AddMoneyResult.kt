package com.dvm.appd.bosm.dbg.profile.views

sealed class AddMoneyResult {
    object Success:AddMoneyResult()
    data class Failure(val message:String):AddMoneyResult()
}