package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class ShowsPojo(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("tickets_available")
    val ticketsAvailable: Boolean,

    @SerializedName("allow_bitsians")
    val allowBitsians: Boolean,

    @SerializedName("allow_participants")
    val allowParticipants: Boolean
)