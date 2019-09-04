package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class ChildUserShowPojo(

    @SerializedName("show_name")
    val showName: String,

    @SerializedName("unused_count")
    val unusedCount: Int,

    @SerializedName("used_count")
    val usedCount: Int
)