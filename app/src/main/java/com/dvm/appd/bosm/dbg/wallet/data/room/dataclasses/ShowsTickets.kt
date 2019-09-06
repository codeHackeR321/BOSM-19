package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class ShowsTickets(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val showId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "tickets_available")
    val ticketsAvailable: Boolean,

    @ColumnInfo(name = "allow_bitsians")
    val allowBitsian: Boolean,

    @ColumnInfo(name = "allow_participants")
    val allowParticipants: Boolean
)