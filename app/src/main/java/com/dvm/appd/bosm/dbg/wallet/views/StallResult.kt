package com.dvm.appd.bosm.dbg.wallet.views

sealed class StallResult {
    object Failure:StallResult()
    object Success:StallResult()
}