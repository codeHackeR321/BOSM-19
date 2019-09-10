package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class ShowsPojo(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Int

)