package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class OrderItemsPojo(

    @SerializedName("name")
    val itemName: String,

    @SerializedName("id")
    val itemId: String,

    @SerializedName("quantity")
    val quantity: String,

    @SerializedName("unit_price")
    val price: String
)