package com.dvm.appd.bosm.dbg.wallet.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStalls(stalls:List<StallData>)

    @Query("SELECT * FROM stalls")
    fun getAllStalls():Flowable<List<StallData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStallItems(items : List<StallItemsData>)

    @Query("SELECT * FROM stall_items where stallId = :stallId")
    fun getItemsForStallById(stallId:Int):Flowable<List<StallItemsData>>

    @Query("DELETE FROM stall_items")
    fun deleteAllStallItems()

    @Query("DELETE FROM stalls")
    fun deleteAllStalls()
}