package com.dvm.appd.bosm.dbg.wallet.data.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.WalletService
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.dataclasses.StallsPojo
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData
import io.reactivex.Completable
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
}