package com.dvm.appd.bosm.dbg.events.data.room

import androidx.room.*
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.DELETE

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMiscEventData(events: List<MiscEventsData>): Completable

    @Query("UPDATE misc_table SET favourite = :mark WHERE event_id = :id")
    fun updateMiscFavourite(id: String, mark: Int): Completable

    @Query("UPDATE misc_table SET event_name = :name, event_venue = :venue, event_time = :time, event_description = :description, event_day = :day, organiser = :organiser WHERE event_id = :id")
    fun updateMiscData(id: String, name: String, venue: String, time: Long, description: String, day: String, organiser: String): Completable

    @Query("DELETE FROM misc_table WHERE event_id = :id")
    fun deleteMiscEvent(id: String): Completable

    @Query("SELECT * FROM misc_table ORDER BY event_day, event_time")
    fun getMiscEvents(): Flowable<List<MiscEventsData>>

    @Query("SELECT DISTINCT sport_name FROM sports_table")
    fun getSportsName(): Flowable<List<String>>

    //Sports
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSportsData(sportsData: List<SportsData>): Completable

    @Query("DELETE FROM sports_table WHERE match_no=:matchno")
    fun deleteSportsData(matchno: Int): Completable

    @Query("SELECT DISTINCT gender FROM sports_table  WHERE sport_name = :name")
    fun getDistinctGenderForSport(name: String): Single<List<String>>

    @Query("SELECT * FROM sports_table WHERE sport_name = :name")
    fun getSportDataForSport(name: String): Single<List<SportsData>>
}