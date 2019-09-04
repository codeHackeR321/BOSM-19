package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
data class Ratings(

    @ColumnInfo(name = "order_id")
    @PrimaryKey
    val orderId: Int,

    @ColumnInfo(name = "rating")
    val rating: Int
)