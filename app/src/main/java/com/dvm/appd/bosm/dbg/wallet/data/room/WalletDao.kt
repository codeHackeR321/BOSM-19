package com.dvm.appd.bosm.dbg.wallet.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStalls(stalls:List<StallData>)

    @Query("SELECT * FROM stalls")
    fun getAllStalls():Flowable<List<StallData>>
}