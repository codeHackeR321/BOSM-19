package com.dvm.appd.bosm.dbg.wallet.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStalls(stalls:List<StallData>)

    @Query("SELECT * FROM stalls")
    fun getAllStalls():Flowable<List<StallData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStallItems(items : List<StallItemsData>)

    @Query("DELETE FROM stall_items")
    fun deleteAllStallItems()

    @Query("DELETE FROM stalls")
    fun deleteAllStalls()

    @Query("SELECT orders.id as orderId, otp, otp_seen as otpSeen, status, price as totalPrice, vendor, name as itemName, item_id as itemId, quantity, unit_price as price FROM orders LEFT JOIN order_items ON orders.id = order_items.order_id ORDER BY order_id")
    fun getOrdersData(): Flowable<List<ChildOrdersData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrders(orders: List<OrderData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrderItems(orderItems: List<OrderItemsData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItems(cartItems: CartData): Completable

    @Query("DELETE FROM cart_data")
    fun clearCart(): Completable

    @Query("DELETE FROM cart_data WHERE item_id = :itemId")
    fun deleteCartItem(itemId: Int): Completable

    @Query("SELECT * FROM cart_data ORDER BY vendor_id")
    fun getAllCartItems(): Flowable<List<CartData>>

    @Query("SELECT cart_data.item_id AS itemId, stall_items.itemName as itemName, cart_data.vendor_id AS vendorId, stalls.stallName AS vendorName, cart_data.quantity AS quantity, stall_items.price AS price FROM cart_data LEFT JOIN stall_items ON cart_data.item_id = stall_items.itemId LEFT JOIN stalls ON cart_data.vendor_id = stalls.stallId ")
    fun getAllModifiedCartItems(): Flowable<List<ChildCartData>>

    @Query("SELECT itemId, itemName, stallId, isAvailable, price, COALESCE(cart_data.quantity, 0) AS quantity FROM stall_items LEFT JOIN cart_data ON itemId = item_id WHERE stallId = :stallId")
    fun getModifiedStallItemsById(stallId: Int): Flowable<List<ModifiedStallItemsData>>

    @Query("UPDATE cart_data SET quantity = :quantity WHERE item_id = :itemId")
    fun updateCartItem(quantity: Int, itemId: Int): Completable
}