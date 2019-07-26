package com.dvm.appd.bosm.dbg.events.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "misc_table")
data class MiscEventsData(

    @PrimaryKey
    @ColumnInfo(name = "event_name")
    var name: String,

    @ColumnInfo(name = "event_venue")
    var venue: String,

//    @ColumnInfo(name = "event_time")
//    var time: String,

    @ColumnInfo(name = "event_description")
    var description: String,

    @ColumnInfo(name = "event_day")
    var day: String,

    @ColumnInfo(name = "organiser")
    var organiser: String
)