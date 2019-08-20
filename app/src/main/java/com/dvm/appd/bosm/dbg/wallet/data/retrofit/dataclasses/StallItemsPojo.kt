package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class StallItemsPojo(

    @SerializedName("id")
    val itemId: Int,

    @SerializedName("name")
    val itemName: String,

    @SerializedName("vendor_id")
    val stallId: Int,

    @SerializedName("is_veg")
    val isVeg: Boolean,

    @SerializedName("is_available")
    val isAvailable: Boolean,

    val price: Int
)