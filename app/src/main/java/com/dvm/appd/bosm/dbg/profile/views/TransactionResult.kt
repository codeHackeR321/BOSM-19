package com.dvm.appd.bosm.dbg.profile.views

sealed class TransactionResult {
    object Success:TransactionResult()
    data class Failure(val message:String):TransactionResult()
}