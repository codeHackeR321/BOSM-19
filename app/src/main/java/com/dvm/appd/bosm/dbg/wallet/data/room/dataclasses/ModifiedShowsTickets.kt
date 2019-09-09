package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

data class ModifiedShowsTickets(

    val showId: Int,

    val name: String,

    val price: Int,

    val ticketsAvailable: Boolean,

    val allowBitsian: Boolean,

    val allowParticipants: Boolean,

    val cartId: Int,

    val quantity: Int
)