package com.dvm.appd.bosm.dbg.events.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_events")
data class FavNamesData(

    @ColumnInfo(name = "event_name")
    @PrimaryKey
    var name: String
)
