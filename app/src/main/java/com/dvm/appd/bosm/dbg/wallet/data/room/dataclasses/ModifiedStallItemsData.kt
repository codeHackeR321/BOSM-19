package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ModifiedStallItemsData(

    val itemId:Int,

    val itemName:String,

    val stallId:Int,

    val price:Int,

    val isAvailable:Boolean,

    val quantity: Int
)