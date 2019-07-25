package com.dvm.appd.bosm.dbg.events.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "misc_table")
data class MiscEventsData(

    @ColumnInfo(name = "event_name")
    var name: String,

    @ColumnInfo(name = "event_location")
    var location: String,

    @ColumnInfo(name = "event_time")
    var time: String,

    @ColumnInfo(name = "event_description")
    var description: String,

    @ColumnInfo(name = "event_day")
    var day: String
)