package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ModifiedOrdersData(

    val orderId: String,

    val otp: String,

    val otpSeen: String,

    val status: String,

    val totalPrice: String,

    val vendor: String,

    val items: List<ModifiedItemsClass>
)