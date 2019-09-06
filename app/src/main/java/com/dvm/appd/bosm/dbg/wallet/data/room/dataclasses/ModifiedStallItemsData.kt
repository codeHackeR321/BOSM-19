package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ModifiedStallItemsData(

    val itemId:Int,

    val itemName:String,

    val stallId:Int,

    val category: String,

    val price:Int,

    val quantity: Int,

    val isVeg: Boolean
)