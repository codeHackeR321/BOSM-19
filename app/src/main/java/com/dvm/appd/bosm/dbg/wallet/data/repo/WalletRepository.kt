package com.dvm.appd.bosm.dbg.wallet.data.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository
import com.dvm.appd.bosm.dbg.profile.views.TransactionResult
import com.dvm.appd.bosm.dbg.shared.MoneyTracker
import com.dvm.appd.bosm.dbg.shared.NetworkChecker
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
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class WalletRepository(val walletService: WalletService, val walletDao: WalletDao, val authRepository: AuthRepository, val moneyTracker: MoneyTracker, val networkChecker: NetworkChecker) {

    private val jwt = authRepository.getUser().toSingle().flatMap { return@flatMap Single.just("jwt ${it.jwt}") }
    private val userId = authRepository.getUser().toSingle().flatMap { return@flatMap Single.just(it.userId.toInt()) }
    // To be implemented after profile (when userId available)  sharedPreferences.getString("ID", "1")?.toInt()
    init {

        val db = FirebaseFirestore.getInstance()

        db.collection("orders").whereEqualTo("userid", userId.blockingGet())
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    Log.e("WalletRepo", "Listen Failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    Log.d("WalletRepo", "Firebase $snapshot")
                    updateOrders().subscribe()
                }
            }
    }

    fun fetchAllStalls(): Completable {
        Log.d("check", "called")
        return walletService.getAllStalls()
            .doOnSuccess { response ->
                Log.d("check", response.body().toString())
                Log.d("checkfetch", response.code().toString())
                when (response.code()) {
                    200 -> {
                        var stallList: List<StallData> = emptyList()
                        var itemList: List<StallItemsData> = emptyList()
                        response.body()!!.forEach { stall ->
                            stallList = stallList.plus(stall.toStallData())
                            itemList = itemList.plus(stall.toStallItemsData())
                        }
                        Log.d("checkwmr", itemList.toString())
                        walletDao.deleteAllStalls()
                        walletDao.deleteAllStallItems()
                        walletDao.insertAllStalls(stallList)
                        walletDao.insertAllStallItems(itemList)

                    }
                    else -> {
                        Log.d("checke", response.errorBody()!!.string())
                    }
                }
            }.ignoreElement()
            .doOnError {
                Log.d("checke", it.message)
            }.subscribeOn(Schedulers.io())

    }

    fun getAllStalls(): Observable<List<StallData>> {
        Log.d("check", "calledg")

        if (networkChecker.isConnected() == false) {
            return walletDao.getAllStalls().toObservable().subscribeOn(Schedulers.io())
                .doOnError {
                    Log.d("checkre", it.toString())
                }
        }

        return fetchAllStalls().andThen(walletDao.getAllStalls().toObservable())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.d("checke", it.toString())
            }
    }

    fun getItemsForStall(stallId: Int): Observable<List<ModifiedStallItemsData>> {

        return walletDao.getModifiedStallItemsById(stallId, true).toObservable()
            .doOnError {
              Log.d("checke",it.toString())
            }
            .subscribeOn(Schedulers.io())
    }

    private fun StallsPojo.toStallData(): StallData {
        return StallData(stallId, stallName, closed)
    }

    private fun StallsPojo.toStallItemsData(): List<StallItemsData> {

        var itemList: List<StallItemsData> = emptyList()

        items.forEach {
            itemList =
                itemList.plus(StallItemsData(it.itemId, it.itemName, it.stallId, it.price, it.isAvailable, it.isVeg))
        }
        return itemList
    }

    private fun updateOrders(): Completable{
        return walletService.getAllOrders(jwt.blockingGet().toString())
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
                        throw Exception("400")
                    }

                    401 -> {
                        Log.d("GetOrder", "Success Error: 401")
                        throw Exception("401")
                    }

                    403 -> {
                        Log.d("GetOrder", "Success Error: 403")
                        throw Exception("403")
                    }

                    404 -> {
                        Log.d("GetOrder", "Success Error: 404")
                        throw Exception("404 shell id doesn't exist")
                    }

                    412 -> {
                        Log.d("GetOrder", "Success Error: 412")
                        throw Exception("412")
                    }
                }
            }
            .doOnError {
                Log.e("GetOrder", "Error", it)
            }
            .ignoreElement()
            .subscribeOn(Schedulers.io())
    }

    private fun AllOrdersPojo.toOrderData(): List<OrderData> {

        var orderData: MutableList<OrderData> = arrayListOf()
        orders.forEach {
            orderData.add(
                OrderData(
                    orderId = it.id,
                    otp = it.otp,
                    otpSeen = it.otpSeen,
                    status = it.status,
                    price = it.price,
                    vendor = it.vendor.vendorName
                )
            )
        }
        return orderData
    }

    private fun AllOrdersPojo.toOrderItemsData(): List<OrderItemsData> {

        var orderItemsData: MutableList<OrderItemsData> = arrayListOf()
        orders.forEach {
            it.items.forEach { orderItemsPojo ->
                orderItemsData.add(
                    OrderItemsData(
                        itemName = orderItemsPojo.itemName,
                        itemId = orderItemsPojo.itemId,
                        quantity = orderItemsPojo.quantity,
                        price = orderItemsPojo.price,
                        orderId = it.id,
                        id = 0
                    )
                )
            }
        }

        return orderItemsData
    }

    fun getAllOrders(): Flowable<List<ModifiedOrdersData>> {

        return walletDao.getOrdersData().subscribeOn(Schedulers.io())
            .flatMap {

                var ordersList: MutableList<ModifiedOrdersData> = arrayListOf()
                var itemsList: MutableList<ModifiedItemsData> = arrayListOf()

                for ((index, item) in it.listIterator().withIndex()) {

                    itemsList.add(
                        ModifiedItemsData(
                            itemName = item.itemName, itemId = item.itemId,
                            quantity = item.quantity, price = item.price
                        )
                    )

                    if (index != it.lastIndex && it[index].orderId != it[index + 1].orderId) {

                        ordersList.add(
                            ModifiedOrdersData(
                                orderId = item.orderId,
                                otp = item.otp,
                                otpSeen = item.otpSeen
                                ,
                                status = item.status,
                                totalPrice = item.totalPrice,
                                vendor = item.vendor,
                                items = itemsList
                            )
                        )

                        itemsList = arrayListOf()
                    } else if (index == it.lastIndex) {
                        ordersList.add(
                            ModifiedOrdersData(
                                orderId = item.orderId,
                                otp = item.otp,
                                otpSeen = item.otpSeen
                                ,
                                status = item.status,
                                totalPrice = item.totalPrice,
                                vendor = item.vendor,
                                items = itemsList
                            )
                        )

                        itemsList = arrayListOf()
                    }
                }

                return@flatMap Flowable.just(ordersList)

            }
    }

    fun getOrderById(orderId: Int): Flowable<ModifiedOrdersData>{
        return walletDao.getOrderById(orderId).subscribeOn(Schedulers.io())
            .flatMap {

                var order: ModifiedOrdersData
                var itemsList: MutableList<ModifiedItemsData> = arrayListOf()

                it.forEach{item ->

                    itemsList.add(
                        ModifiedItemsData(
                            itemName = item.itemName, itemId = item.itemId,
                            quantity = item.quantity, price = item.price
                        )
                    )
                }

                order = ModifiedOrdersData(it.last().orderId, it.last().otp, it.last().otpSeen, it.last().status, it.last().totalPrice, it.last().vendor, itemsList)

                return@flatMap Flowable.just(order)
            }
    }

    fun insertCartItems(cartItem: CartData): Completable {
        return walletDao.insertCartItems(cartItem).subscribeOn(Schedulers.io())
    }

    fun deleteCartItem(itemId: Int): Completable {
        return walletDao.deleteCartItem(itemId).subscribeOn(Schedulers.io())
    }

    fun placeOrder(): Completable {

        return walletDao.getAllCartItems().subscribeOn(Schedulers.io())
            .firstOrError()
            .map {

                var orderJsonObject = JsonObject()
                var orders = JsonObject()
                var items: MutableList<Pair<Int, Int>> = arrayListOf()
                for ((index, item) in it.listIterator().withIndex()) {

                    items.add(Pair(item.itemId, item.quantity))

                    if (index != it.lastIndex && it[index].vendorId != it[index + 1].vendorId) {

                        orders.add("${it[index].vendorId}", JsonObject().also {
                            items.forEach { pair ->
                                it.addProperty(pair.first.toString(), pair.second)
                            }
                        })

                        items = arrayListOf()
                    } else if (index == it.lastIndex) {

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
                return@flatMapCompletable walletService.placeOrder(jwt.blockingGet().toString(), body).subscribeOn(Schedulers.io())
                    .doOnSuccess {response ->

                        when (response.code()) {

                            200 -> {
                                walletDao.clearCart().subscribeOn(Schedulers.io()).subscribe()
                            }

                            400 -> {
                                Log.d("PlaceOrder", "Success Error: 400")
                                throw Exception("400 Error in Key or vendor Id or quantity")
                            }

                            401 -> {
                                Log.d("PlaceOrder", "Success Error: 401")
                                throw Exception("401 wrong credentials")
                            }

                            403 -> {
                                Log.d("PlaceOrder", "Success Error: 403")
                                throw Exception("403 user banned")
                            }

                            404 -> {
                                Log.d("PlaceOrder", "Success Error: 404")
                                throw Exception("404 item not available")
                            }

                            412 -> {
                                Log.d("PlaceOrder", "Success Error: 412")
                                throw Exception("412 vendor closed")
                            }
                        }

                    }
                    .doOnError {
                        Log.e("PlaceOrder", "Error", it)
                    }
                    .ignoreElement()
            }
    }

    fun getAllModifiedCartItems(): Flowable<List<ModifiedCartData>> {
        return walletDao.getAllModifiedCartItems().subscribeOn(Schedulers.io())
    }

    fun updateCartItems(itemId: Int, quantity: Int): Completable {
        return walletDao.updateCartItem(quantity, itemId).subscribeOn(Schedulers.io())
    }

    fun updateOtpSeen(orderId: Int): Completable {

        val body = JsonObject().also {
            it.addProperty("order_id", orderId)
        }

        return walletService.makeOtpSeen(jwt.blockingGet().toString(), body).subscribeOn(Schedulers.io())
            .doOnSuccess {response ->

                when (response.code()) {

                    200 -> {

                    }

                    400 -> {
                        throw Exception("400")
                    }

                    401 -> {
                        throw Exception("401")
                    }

                    403 -> {
                        throw Exception("403")
                    }

                    404 -> {
                        throw Exception("404")
                    }

                    412 -> {
                        throw Exception("412")
                    }

                }
            }
            .doOnError {

            }
            .ignoreElement()
    }

    fun addMoneyBitsian(amount: Int): Single<TransactionResult> {

        val body = JsonObject().also {
            it.addProperty("amount", amount)
        }

        Log.d("check", body.toString())

        return authRepository.getUser()
            .toSingle()
            .flatMap {
                walletService.addMoneyBitsian("jwt ${it.jwt}", body).map { response ->
                    Log.d("check", response.code().toString())

                    when (response.code()) {
                        200 -> TransactionResult.Success
                        in 400..499 -> TransactionResult.Failure(response.errorBody()!!.string())
                        else -> TransactionResult.Failure("Something went wrong!!")
                    }
                }.doOnError { throwable ->
                    Log.d("checke", throwable.toString())
                }
            }.subscribeOn(Schedulers.io())

    }

    fun getBalance(): Flowable<Int> {
        return moneyTracker.getBalance().doOnError {
            Log.d("checke", it.toString())
        }
    }

    fun transferMoney(id:Int,amount:Int):Single<TransactionResult>{

        val body = JsonObject().also {
            it.addProperty("id",id)
            it.addProperty("amount",amount)
        }
        Log.d("check",body.toString())
        return authRepository.getUser()
            .toSingle()
            .flatMap {
                walletService.transferMoney("jwt ${it.jwt}",body).map {response ->
                    Log.d("check",response.code().toString())
                    when(response.code()){
                        200 -> TransactionResult.Success
                        in 400..499 -> TransactionResult.Failure(response.errorBody()!!.string())
                        else -> TransactionResult.Failure("Something went wrong!!")

                    }
                }.doOnError { throwable ->
                    Log.d("checke", throwable.toString())
                }
            }.subscribeOn(Schedulers.io())

    }
}