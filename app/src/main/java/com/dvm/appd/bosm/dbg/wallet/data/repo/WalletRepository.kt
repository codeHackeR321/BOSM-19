package com.dvm.appd.bosm.dbg.wallet.data.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.WalletService
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.AllOrdersPojo
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WalletRepository(val walletService: WalletService, val walletDao: WalletDao) {


    // To be implemented after profile (when userId available)
//    init {
//
//        val db = FirebaseFirestore.getInstance()
//
//        db.collection("orders").addSnapshotListener { snapshot, exception ->
//
//            if (exception != null){
//                Log.e("WalletRepo", "Listen failed", exception)
//                return@addSnapshotListener
//            }
//            if (snapshot != null){
//
//                snapshot.documents.forEach {
//
//                }
//            }
//        }
//    }

    fun fetchAllStalls(): Completable {
        Log.d("check", "called")
        return walletService.getAllStalls()
            .doOnSuccess { response ->
                Log.d("check", response.body().toString())
                Log.d("checkfetch",response.code().toString())
                when (response.code()) {
                    200 -> {
                        var stallList: List<StallData> = emptyList()
                        var itemList: List<StallItemsData> = emptyList()
                        response.body()!!.forEach { stall ->
                            stallList = stallList.plus(stall.toStallData())
                            itemList = itemList.plus(stall.toStallItemsData())
                        }
                        walletDao.deleteAllStalls()
                        walletDao.deleteAllStallItems()
                        walletDao.insertAllStalls(stallList)
                        walletDao.insertAllStallItems(itemList)

                    }

                    else -> Log.d("checke", response.body().toString())
                }
            }.doOnError {
                Log.d("checke", it.message)
            }.ignoreElement()
            .subscribeOn(Schedulers.io())

    }

    fun getAllStalls(): Observable<List<StallData>> {
        Log.d("check", "calledg")
        return walletDao.getAllStalls().toObservable().subscribeOn(Schedulers.io())
            .doOnError {
                Log.d("checkre",it.toString())
            }
    }

    fun getItemsForStall(stallId: Int): Flowable<List<ModifiedStallItemsData>>{

        return walletDao.getModifiedStallItemsById(stallId)
            .doOnError {

            }
            .subscribeOn(Schedulers.io())
    }

    fun StallsPojo.toStallData(): StallData {
        return StallData(stallId, stallName, closed)
    }

    fun StallsPojo.toStallItemsData(): List<StallItemsData> {

        var itemList: List<StallItemsData> = emptyList()

        items.forEach {
            itemList = itemList.plus(StallItemsData(it.itemId, it.itemName, it.stallId, it.price, it.isAvailable))
        }
        return itemList
    }

    fun updateOrders(): Completable{
        return walletService.getAllOrders()
            .doOnSuccess {response ->
                when(response.code()){
                    200 -> {

                        var orders: MutableList<OrderData> = arrayListOf()
                        var orderItems: MutableList<OrderItemsData> = arrayListOf()

                        response.body()!!.forEach {
                            orders.addAll(it.toOrderData())
                            orderItems.addAll(it.toOrderItemsData())
                        }

                        Log.d("Orders", orders.toString())
                        Log.d("Orders", orderItems.toString())
                        walletDao.insertNewOrders(orders)
                        walletDao.insertNewOrderItems(orderItems)
                    }

                    400 -> {
                        Log.d("GetOrder", "Success Error: 400")
                    }

                    401 -> {
                        Log.d("GetOrder", "Success Error: 401")
                    }

                    403 -> {
                        Log.d("GetOrder", "Success Error: 403")
                    }

                    404 -> {
                        Log.d("GetOrder", "Success Error: 404")
                    }

                    412 -> {
                        Log.d("GetOrder", "Success Error: 412")
                    }
                }
            }
            .doOnError {
                Log.e("GetOrder", "Error", it)
            }
            .ignoreElement()
            .subscribeOn(Schedulers.io())
    }

    private fun AllOrdersPojo.toOrderData(): List<OrderData>{

        var orderData: MutableList<OrderData> = arrayListOf()
        orders.forEach {
            orderData.add(OrderData(orderId = it.id, otp = it.otp, otpSeen = it.otpSeen, status = it.status, price = it.price, vendor = it.vendor.vendorName))
        }
        return orderData
    }

    private fun AllOrdersPojo.toOrderItemsData(): List<OrderItemsData>{

        var orderItemsData: MutableList<OrderItemsData> = arrayListOf()
        orders.forEach {
            it.items.forEach { orderItemsPojo ->
                orderItemsData.add(OrderItemsData(itemName = orderItemsPojo.itemName, itemId = orderItemsPojo.itemId, quantity = orderItemsPojo.quantity, price = orderItemsPojo.price, orderId = it.id, id = 0))
            }
        }

        return orderItemsData
    }

    fun getAllOrders(): Flowable<List<ModifiedOrdersData>>{

        return walletDao.getOrdersData().subscribeOn(Schedulers.io())
            .flatMap {

                var ordersList: MutableList<ModifiedOrdersData> = arrayListOf()
                var itemsList: MutableList<ModifiedItemsData> = arrayListOf()

                for ((index, item) in it.listIterator().withIndex()){

                    itemsList.add(ModifiedItemsData(itemName = item.itemName, itemId = item.itemId,
                        quantity = item.quantity, price = item.price))

                    if (index != it.lastIndex && it[index].orderId != it[index + 1].orderId){

                        ordersList.add(ModifiedOrdersData(orderId = item.orderId, otp = item.otp, otpSeen = item.otpSeen
                            , status = item.status, totalPrice = item.totalPrice, vendor = item.vendor, items = itemsList))

                        itemsList = arrayListOf()
                    }
                    else if (index == it.lastIndex){
                        ordersList.add(ModifiedOrdersData(orderId = item.orderId, otp = item.otp, otpSeen = item.otpSeen
                            , status = item.status, totalPrice = item.totalPrice, vendor = item.vendor, items = itemsList))

                        itemsList = arrayListOf()
                    }
                }

                return@flatMap Flowable.just(ordersList)

            }
    }

    fun insertCartItems(cartItem: CartData): Completable{
        return walletDao.insertCartItems(cartItem).subscribeOn(Schedulers.io())
    }

    fun deleteCartItem(itemId: Int): Completable{
        return walletDao.deleteCartItem(itemId).subscribeOn(Schedulers.io())
    }

    fun placeOrder(): Completable{

        return walletDao.getAllCartItems().subscribeOn(Schedulers.io())
            .firstOrError()
            .map {

                var orderJsonObject = JsonObject()
                var orders = JsonObject()
                var items: MutableList<Pair<Int, Int>> = arrayListOf()
                for ((index, item) in it.listIterator().withIndex()){

                    items.add(Pair(item.itemId, item.quantity))

                    if (index != it.lastIndex  &&  it[index].vendorId != it[index + 1].vendorId){

                        orders.add("${it[index].vendorId}", JsonObject().also {
                            items.forEach { pair ->
                                it.addProperty(pair.first.toString(), pair.second)
                            }
                        })

                        items = arrayListOf()
                    }
                    else if (index == it.lastIndex){

                        orders.add("${it[index].vendorId}", JsonObject().also {
                            items.forEach { pair ->
                                it.addProperty(pair.first.toString(), pair.second)
                            }
                        })

                        items = arrayListOf()
                    }

                }

                orderJsonObject.add("orderdict", orders)
                Log.d("PlaceOrder", "$orderJsonObject")
                return@map orderJsonObject
            }
            .flatMapCompletable {body ->
                return@flatMapCompletable walletService.placeOrder(body).subscribeOn(Schedulers.io())
                    .doOnSuccess {response ->

                        when(response.code()){

                            200 -> {
                                walletDao.clearCart().subscribeOn(Schedulers.io()).subscribe()
                            }

                            400 -> Log.d("PlaceOrder", "Success Error: 400")

                            401 -> Log.d("PlaceOrder", "Success Error: 401")

                            403 -> Log.d("PlaceOrder", "Success Error: 403")

                            404 -> Log.d("PlaceOrder", "Success Error: 404")

                            412 -> Log.d("PlaceOrder", "Success Error: 412")
                        }

                    }
                    .doOnError {
                        Log.e("PlaceOrder", "Error", it)
                    }
                    .ignoreElement()
            }
    }

    fun getAllModifiedCartItems(): Flowable<Pair<List<ModifiedCartData>, Int>>{
        return walletDao.getAllModifiedCartItems().subscribeOn(Schedulers.io())
            .flatMap {

                var finalCartData: Pair<List<ModifiedCartData>, Int>
                var cartData: MutableList<ModifiedCartData> = arrayListOf()
                var cartItemsData: MutableList<ModifiedCartItemsData> = arrayListOf()
                var totalPrice = 0

                for ((index, item) in it.listIterator().withIndex()){

                    cartItemsData.add(ModifiedCartItemsData(item.itemId, item.itemName, item.quantity, item.price))
                    totalPrice += item.quantity * item.price

                    if (index != it.lastIndex  &&  it[index].vendorId != it[index + 1].vendorId){

                        cartData.add(ModifiedCartData(item.vendorId, item.vendorName, cartItemsData))
                        cartItemsData = arrayListOf()
                    }
                    else if (index == it.lastIndex){

                        cartData.add(ModifiedCartData(item.vendorId, item.vendorName, cartItemsData))
                        cartItemsData = arrayListOf()
                    }
                }

                finalCartData = Pair(cartData, totalPrice)
                return@flatMap Flowable.just(finalCartData)
            }
    }

    fun updateCartItems(itemId: Int, quantity: Int): Completable{
        return walletDao.updateCartItem(quantity, itemId).subscribeOn(Schedulers.io())
    }
}