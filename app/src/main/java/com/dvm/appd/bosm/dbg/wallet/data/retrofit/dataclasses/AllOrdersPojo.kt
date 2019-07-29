package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class AllOrdersPojo(

    @SerializedName("orders")
    val orders: List<OrderPojo>,

    @SerializedName("id")
    val id: String,

    @SerializedName("timestamp")
    val timestamp: String
)