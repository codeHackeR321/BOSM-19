package com.dvm.appd.bosm.dbg.events.data

import com.google.gson.annotations.SerializedName

data class EPCData(

    @SerializedName("short_summary")
    val summary: String,

    @SerializedName("link")
    val link: String
)