package com.dvm.appd.bosm.dbg.wallet.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.OrderData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.OrderItemsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStalls(stalls:List<StallData>)

    @Query("SELECT * FROM stalls")
    fun getAllStalls():Flowable<List<StallData>>

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flowable<List<OrderData>>

    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    fun getOrderItems(orderId: String): Flowable<List<OrderItemsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrders(orders: List<OrderData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrderItems(orderItems: List<OrderItemsData>)
}