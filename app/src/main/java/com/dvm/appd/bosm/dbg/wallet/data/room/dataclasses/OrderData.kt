package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val orderId: String,

    @ColumnInfo(name = "otp")
    val otp: String,

    @ColumnInfo(name = "otp_seen")
    val otpSeen: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "vendor")
    val vendor: String //vendor id or name
)