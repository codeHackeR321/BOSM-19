package com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemsData(

    @ColumnInfo(name = "name")
    val itemName: String,

    @ColumnInfo(name = "item_id")
    val itemId: String,

    @ColumnInfo(name = "quantity")
    val quantity: String,

    @ColumnInfo(name = "unit_price")
    val price: String,

    @ColumnInfo(name = "order_id")
    val orderId: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int
)