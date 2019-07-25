package com.dvm.appd.bosm.dbg.events.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports_names")
data class SportsNamesData(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String
)
