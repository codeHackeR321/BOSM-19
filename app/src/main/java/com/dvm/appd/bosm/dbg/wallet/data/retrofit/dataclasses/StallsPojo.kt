package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class StallsPojo(

    @SerializedName("id")
    val stallId: String,

    @SerializedName("name")
    val stallName: String,

    val closed: Boolean
)