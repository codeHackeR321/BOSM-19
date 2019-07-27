package com.dvm.appd.bosm.dbg.events.data.room

import androidx.room.*
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMiscEventData(events: List<MiscEventsData>): Completable

    @Query("UPDATE misc_table SET favourite = :mark WHERE event_id = :id")
    fun updateMiscFavourite(id: String, mark: Int): Completable

    @Query("UPDATE misc_table SET event_name = :name, event_venue = :venue, event_time = :time, event_description = :description, event_day = :day, organiser = :organiser WHERE event_id = :id")
    fun updateMiscData(id: String, name: String, venue: String, time: String, description: String, day: String, organiser: String): Completable

    @Query("DELETE FROM misc_table WHERE event_id = :id")
    fun deleteMiscEvent(id: String): Completable

    @Query("SELECT * FROM misc_table ORDER BY event_day, event_time")
    fun getMiscEvents(): Flowable<List<MiscEventsData>>

    @Query("SELECT * FROM sports_names")
    fun getSportsName(): Flowable<List<SportsNamesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSportsName(names: List<SportsNamesData>): Completable

    @Query("SELECT group_number FROM sports_names WHERE name = :name")
    fun getSportGroup(name: String): String
}