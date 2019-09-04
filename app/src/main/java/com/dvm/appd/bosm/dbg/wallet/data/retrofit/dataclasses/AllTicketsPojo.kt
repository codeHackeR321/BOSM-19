package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class AllTicketsPojo(

    @SerializedName("shows")
    val shows: List<ShowsPojo>,

    @SerializedName("combos")
    val combos: List<ComboPojo>
)