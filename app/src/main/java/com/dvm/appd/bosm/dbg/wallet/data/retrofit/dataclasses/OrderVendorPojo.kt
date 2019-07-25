package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class OrderVendorPojo(

    @SerializedName("name")
    val vendorName: String,

    @SerializedName("id")
    val vendorId: String
)