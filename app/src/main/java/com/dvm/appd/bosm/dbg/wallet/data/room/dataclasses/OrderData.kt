package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val orderId: Int,

    @ColumnInfo(name = "otp")
    val otp: Int,

    @ColumnInfo(name = "otp_seen")
    val otpSeen: Boolean,

    @ColumnInfo(name = "status")
    val status: Int,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "vendor")
    val vendor: String //vendor id or name
)