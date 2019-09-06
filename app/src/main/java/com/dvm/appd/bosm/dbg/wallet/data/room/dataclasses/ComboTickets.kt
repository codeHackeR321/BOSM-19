package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "combos")
data class ComboTickets(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val comboId: Int,

    @ColumnInfo(name = "combo_name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "allow_bitsians")
    val allowBitsian: Boolean,

    @ColumnInfo(name = "allow_participants")
    val allowParticipants: Boolean
)