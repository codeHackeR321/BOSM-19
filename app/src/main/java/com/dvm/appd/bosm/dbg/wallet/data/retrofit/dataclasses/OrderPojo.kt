package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class OrderPojo(

    @SerializedName("transaction")
    val transaction: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("items")
    val items: List<OrderItemsPojo>,

    @SerializedName("vendor")
    val vendor: OrderVendorPojo,

    @SerializedName("shell")
    val shell: String,

    @SerializedName("otp_seen")
    val otpSeen: String,

    @SerializedName("otp")
    val otp: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("price")
    val price: String
)