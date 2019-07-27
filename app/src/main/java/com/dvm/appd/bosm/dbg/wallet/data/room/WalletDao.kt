package com.dvm.appd.bosm.dbg.wallet.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ChildOrdersData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.OrderData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.OrderItemsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStalls(stalls:List<StallData>)

    @Query("SELECT * FROM stalls")
    fun getAllStalls():Flowable<List<StallData>>

    @Query("SELECT orders.id as orderId, otp, otp_seen as otpSeen, status, price as totalPrice, vendor, name as itemName, item_id as itemId, quantity, unit_price as price FROM orders LEFT JOIN order_items ON orders.id = order_items.order_id ORDER BY order_id")
    fun getOrdersData(): Flowable<List<ChildOrdersData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrders(orders: List<OrderData>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrderItems(orderItems: List<OrderItemsData>): Completable
}