package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ChildOrdersData(

    val orderId: String,

    val otp: String,

    val otpSeen: String,

    val status: String,

    val totalPrice: String,

    val vendor: String,

    val itemName: String,

    val itemId: String,

    val quantity: String,

    val price: String
)