package com.dvm.appd.bosm.dbg.wallet.data.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.WalletService
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WalletRepository(val walletService: WalletService, val walletDao: WalletDao) {

    fun fetchAllStalls(): Completable {
        Log.d("check", "called")
        return walletService.getAllStalls()
            .doOnSuccess { response ->
                Log.d("check", response.message())
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
    }

    fun getItemsForStall(stallId:Int):Observable<List<StallItemsData>>{
        return walletDao.getItemsForStallById(stallId).toObservable()
            .doOnError{
                Log.d("checkre",it.toString())
            }.subscribeOn(Schedulers.io())
    }
    fun StallsPojo.toStallData(): StallData {
        return StallData(stallId, stallName, closed)
    }

    fun StallsPojo.toStallItemsData(): List<StallItemsData> {

        var itemList: List<StallItemsData> = emptyList()

        items.forEach {
            itemList = itemList.plus(StallItemsData(0, it.itemId, it.itemName, it.stallId, it.price, it.isAvailable))
        }
        return itemList
    }

    fun getAllOrders(): Flowable<List<ModifiedOrdersData>>{

        //Currently manual data insert 
        val orders: MutableList<OrderData> = arrayListOf(OrderData(orderId = "0", otp = "123", otpSeen = "false", status = "1", price = "230", vendor = "Keventers"))
        val orderItems: MutableList<OrderItemsData> = arrayListOf(OrderItemsData(itemName = "Shake", itemId = "102", quantity = "2", price = "115", orderId = "0", id = 0))
        walletDao.insertNewOrders(orders).subscribeOn(Schedulers.io()).subscribe()
        walletDao.insertNewOrderItems(orderItems).subscribeOn(Schedulers.io()).subscribe()

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
}