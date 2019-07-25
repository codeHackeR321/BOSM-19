package com.dvm.appd.bosm.dbg.events.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMiscEventData(events: List<MiscEventsData>): Completable

    @Query("SELECT * FROM misc_table ORDER BY event_day , event_time")
    fun getMiscEvents(): Flowable<List<MiscEventsData>>

    @Query("SELECT * FROM sports_names")
    fun getSportsName(): Flowable<List<SportsNamesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSportsName(names: List<SportsNamesData>): Completable
}