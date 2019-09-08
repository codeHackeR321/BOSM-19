package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ModifiedComboData(

    val comboId: Int,

    val comboName: String,

    val price: Int,

    val allowBitsians: Boolean,

    val allowParticipants: Boolean,

    val shows: List<ChildShows>
)