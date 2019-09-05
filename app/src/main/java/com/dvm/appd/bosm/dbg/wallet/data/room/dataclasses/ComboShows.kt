package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "combo_shows")
data class ComboShows(

    @ColumnInfo(name = "show_id")
    val showId: Int,

    @ColumnInfo(name = "show_name")
    val showName: String,

    @ColumnInfo(name = "combo_id")
    val combo: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int
)