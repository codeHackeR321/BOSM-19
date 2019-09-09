package com.dvm.appd.bosm.dbg.wallet.data.room

import androidx.room.*
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.*
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.QueryName

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

    @Query("SELECT orders.id as orderId, orders.shell as shell, orders.rating as rating, otp, otp_seen as otpSeen, status, price as totalPrice, vendor, name as itemName, item_id as itemId, quantity, unit_price as price FROM orders LEFT JOIN order_items ON orders.id = order_items.order_id ORDER BY order_id DESC")
    fun getOrdersData(): Flowable<List<ChildOrdersData>>

    @Query("SELECT orders.id as orderId, orders.shell as shell, orders.rating as rating, otp, otp_seen as otpSeen, status, price as totalPrice, vendor, name as itemName, item_id as itemId, quantity, unit_price as price FROM orders LEFT JOIN order_items ON orders.id = order_items.order_id WHERE orders.id = :orderId ORDER BY order_id DESC")
    fun getOrderById(orderId: Int): Flowable<List<ChildOrdersData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrders(orders: List<OrderData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewOrderItems(orderItems: List<OrderItemsData>)

    @Query("DELETE FROM order_items")
    fun deleteAllOrderItems()

    @Query("DELETE FROM orders")
    fun deleteAllOrders()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItems(cartItems: CartData): Completable

    @Query("DELETE FROM cart_data")
    fun clearCart(): Completable

    @Query("DELETE FROM cart_data WHERE item_id = :itemId")
    fun deleteCartItem(itemId: Int): Completable

    @Transaction
    fun deleteAndInsertOrders(orders: List<OrderData>){
        deleteAllOrders()
        insertNewOrders(orders)
    }

    @Transaction
    fun deleteAndInsertOrderItems(orderItems: List<OrderItemsData>){
        deleteAllOrderItems()
        insertNewOrderItems(orderItems)
    }

    @Query("SELECT * FROM cart_data ORDER BY vendor_id")
    fun getAllCartItems(): Flowable<List<CartData>>

    @Query("SELECT cart_data.item_id AS itemId, stall_items.itemName as itemName, stall_items.isVeg as isVeg, cart_data.vendor_id AS vendorId, stalls.stallName AS vendorName, cart_data.quantity AS quantity, stall_items.price AS price FROM cart_data LEFT JOIN stall_items ON cart_data.item_id = stall_items.itemId LEFT JOIN stalls ON cart_data.vendor_id = stalls.stallId ")
    fun getAllModifiedCartItems(): Flowable<List<ModifiedCartData>>

    @Query("SELECT itemId, itemName, stallId, category, price, isVeg, COALESCE(cart_data.quantity, 0) AS quantity FROM stall_items LEFT JOIN cart_data ON itemId = item_id WHERE stallId = :stallId AND isAvailable = :available")
    fun getModifiedStallItemsById(stallId: Int, available: Boolean): Flowable<List<ModifiedStallItemsData>>

    @Query("UPDATE cart_data SET quantity = :quantity WHERE item_id = :itemId")
    fun updateCartItem(quantity: Int, itemId: Int): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCombos(combos: List<ComboTickets>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComboShows(comboShows: List<ComboShows>)

    @Query("DELETE FROM combo_shows")
    fun deleteComboShows()

    @Transaction
    fun updateComboShows(comboShows: List<ComboShows>){
        deleteComboShows()
        insertComboShows(comboShows)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllShows(shows: List<ShowsTickets>)

    @Query("SELECT shows.id as showId, shows.price as price, shows.name as name, shows.allow_participants as allowParticipants, shows.allow_bitsians as allowBitsian, shows.tickets_available as ticketsAvailable, COALESCE(tickets_cart.id, 0) as cartId, COALESCE(tickets_cart.quantity, 0) as quantity FROM shows LEFT JOIN tickets_cart ON shows.id = tickets_cart.id WHERE tickets_cart.type = 'Shows' ")
    fun getAllShows(): Flowable<List<ModifiedShowsTickets>>

    @Query("SELECT combos.id as comboId, combos.combo_name as comboName, combos.price as price, combos.allow_bitsians as allowBitsians, combos.allow_participants as allowParticipants, combo_shows.show_id as showId, combo_shows.show_name as showName, COALESCE(tickets_cart.id, 0) as cartId, COALESCE(tickets_cart.quantity, 0) as quantity FROM combos LEFT JOIN combo_shows ON combos.id = combo_shows.combo_id LEFT JOIN tickets_cart ON combos.id = tickets_cart.ticket_id WHERE tickets_cart.type = 'Combos'")
    fun getAllCombos(): Flowable<List<ChildComboData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserShows(userShows: List<UserShows>)

    @Query("SELECT * FROM user_tickets")
    fun getAllUserTickets(): Flowable<List<UserShows>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicketCart(comboCartItem: TicketsCart): Completable

    @Query("UPDATE tickets_cart SET quantity = :quantity WHERE id = :id")
    fun updateTicketsCart(quantity: Int, id: Int): Completable

    @Query("DELETE FROM tickets_cart")
    fun clearTicketsCart(): Completable

    @Query("DELETE FROM tickets_cart WHERE id = :id")
    fun clearTicketsCartItem(id: Int): Completable

    @Query("SELECT * FROM tickets_cart")
    fun getTicketsCart(): Flowable<List<TicketsCart>>

}