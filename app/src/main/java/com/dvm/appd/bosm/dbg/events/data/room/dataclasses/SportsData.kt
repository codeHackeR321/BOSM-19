package com.dvm.appd.bosm.dbg.events.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports_table")
data class SportsData(

    @PrimaryKey
    @ColumnInfo(name = "match_no")
    var match_no: String,

    @ColumnInfo(name = "sport_name")
    var name: String,

    @ColumnInfo(name = "venue")
    var venue: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "round")
    var round: String,

    @ColumnInfo(name = "round_type")
    var round_type: String,

    @ColumnInfo(name = "team_1")
    var team_1: String,

    @ColumnInfo(name = "team_2")
    var team_2: String
)