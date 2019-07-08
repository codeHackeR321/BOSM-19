package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stalls")
data class StallsData (
    @PrimaryKey
    val stallId:String,

    val stallName:String,

    val closed:String
)