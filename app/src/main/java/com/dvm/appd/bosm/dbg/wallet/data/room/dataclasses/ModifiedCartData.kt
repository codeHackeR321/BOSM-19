package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ModifiedCartData(

    var vendorId: Int,

    var vendorName: String,

    var items: List<ModifiedCartItemsData>
)