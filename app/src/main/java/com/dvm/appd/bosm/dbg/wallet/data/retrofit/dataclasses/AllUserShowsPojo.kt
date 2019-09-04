package com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses

import com.google.gson.annotations.SerializedName

data class AllUserShowsPojo(

    @SerializedName("")
    val shows: List<UserShowPojo>
)