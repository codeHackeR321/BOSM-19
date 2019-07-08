package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StallsPojo(


    @SerializedName("name")
    @Expose
    val stallName: String,

    @SerializedName("id")
    @Expose
    val stallId: Int,

    val closed: Boolean
)