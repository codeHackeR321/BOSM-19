package com.dvm.appd.bosm.dbg.events.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import io.reactivex.Flowable

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMiscEventData(events: List<MiscEventsData>)

    @Query("SELECT * FROM misc_table ORDER BY event_day , event_time")
    fun getMiscEvents(): Flowable<List<MiscEventsData>>
}