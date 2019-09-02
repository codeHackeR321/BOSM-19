package com.dvm.appd.bosm.dbg.events.data.repo

import android.annotation.SuppressLint
import android.util.Log
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository.Keys.name
import com.dvm.appd.bosm.dbg.events.data.room.EventsDao
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange

import io.reactivex.Completable
import io.reactivex.Single


class EventsRepository (val eventsDao: EventsDao){

    val db = FirebaseFirestore.getInstance()
    init {

        getSportsDataFromFirestore()

        db.collection("events").document("misc").collection("eventdata")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null){
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null){

                    var miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    for (doc in snapshot.documentChanges){

                        when(doc.type){
                            DocumentChange.Type.ADDED -> {
                                Log.d("DocAdded", doc.document.get("name") as String)
                                miscEvents.add(MiscEventsData(id = doc.document.id, name = (doc.document.get("name")) as String
                                , venue = (doc.document.get("venue") as String), time = ((doc.document.get("timestamp") as Timestamp)).seconds
                                , description = (doc.document.get("description") as String), day = (doc.document.get("day") as String)
                                , organiser = (doc.document.get("organiser") as String), isFavourite = 0))
                            }

                            DocumentChange.Type.MODIFIED -> {
                                Log.d("DocChanged", doc.document.get("name") as String)
                                eventsDao.updateMiscData(id = doc.document.id, name = (doc.document.get("name")) as String
                                    , venue = (doc.document.get("venue") as String), time = ((doc.document.get("timestamp") as Timestamp)).seconds
                                    , description = (doc.document.get("description") as String), day = (doc.document.get("day") as String)
                                    , organiser = (doc.document.get("organiser") as String))
                                    .subscribeOn(Schedulers.io())
                                    .doOnError {
                                        Log.e("EventsRepo", it.message, it)
                                    }
                                    .subscribe()
                            }

                            DocumentChange.Type.REMOVED -> {
                                Log.d("DocRemoved", doc.document.get("name") as String)
                                eventsDao.deleteMiscEvent(doc.document.id).subscribeOn(Schedulers.io())
                                    .doOnError {
                                        Log.e("EventsRepo", it.message, it)
                                    }
                                    .subscribe()
                            }

                        }
                    }

                    Log.d("Events", miscEvents.toString())
                    eventsDao.insertMiscEventData(miscEvents).subscribeOn(Schedulers.io())
                        .doOnComplete {

                        }
                        .doOnError {

                        }
                        .subscribe()
                }
            }

    }

    fun getSportsName(): Flowable<List<String>>{
        return eventsDao.getSportsName().subscribeOn(Schedulers.io())

    }

    fun miscEventDays(): Flowable<List<String>>{
        return eventsDao.getMiscDays().subscribeOn(Schedulers.io())
    }

    fun getDayMiscEvents(day: String): Flowable<List<MiscEventsData>>{
        return eventsDao.getDayMiscEvents(day).subscribeOn(Schedulers.io())
    }

    fun getSportData(name:String): Single<List<SportsData>> {
        return eventsDao.getSportDataForSport(name).subscribeOn(Schedulers.io())
    }

    fun getGenderForSport(name: String):Single<List<String>>{
        return eventsDao.getDistinctGenderForSport(name).subscribeOn(Schedulers.io())
    }

    private fun getSportsDataFromFirestore(){

        var sportsData: MutableList<SportsData> = arrayListOf()
            db.collection("events").document("sports").collection("matches").addSnapshotListener { snapshots, e ->


                            if (e != null) {
                                Log.w("Sports", "listen:error", e)
                                return@addSnapshotListener
                            }

                            for (dc in snapshots!!.documentChanges) {
                                when (dc.type) {
                                    DocumentChange.Type.ADDED -> {
                                        Log.d("sports1", "added doc data : ${dc.document.data}")

                                        sportsData.add(
                                            SportsData(
                                                match_no = dc.document.id.toInt(),
                                                name = dc.document["sport"] as String,
                                                round = dc.document["round_name"] as String,
                                                round_type = dc.document["round_type"] as String,
                                                team_1 = dc.document["team1"] as String,
                                                team_2 = dc.document["team2"] as String,
                                                time = /*(dc.document["timestamp"] as Timestamp).seconds*/1567159831,
                                                venue = dc.document["venue"] as String,
                                                gender = dc.document["gender"] as String,
                                                isScore = dc.document["is_score"] as Boolean,
                                                layout = (dc.document["layout"] as Long).toInt(),
                                                score_1 = (dc.document["score1"] as Long).toString(),
                                                score_2 = (dc.document["score2"] as Long).toString(),
                                                winner1 = dc.document["winner1"] as String,
                                                winner2 = dc.document["winner2"] as String,
                                                winner3 = dc.document["winner3"] as String

                                            )
                                        )

                                    }
                                    DocumentChange.Type.MODIFIED ->{
                                       var position= sportsData.indexOfFirst { it.match_no==dc.document.id.toInt()}

                                       sportsData.set(position,SportsData(
                                           match_no = dc.document.id.toInt(),
                                           name = dc.document["sport"] as String,
                                           round = dc.document["round"] as String,
                                           round_type = dc.document["roundtype"] as String,
                                           team_1 = dc.document["team1"] as String,
                                           team_2 = dc.document["team2"] as String,
                                           time = (dc.document["timestamp"] as Timestamp).seconds,
                                           venue = dc.document["venue"] as String,
                                           gender = dc.document["gender"] as String,
                                           isScore = dc.document["isscore"] as Boolean,
                                           layout = (dc.document["layout"] as Long).toInt(),
                                           score_1 = dc.document["score1"] as String,
                                           score_2 = dc.document["score2"] as String,
                                           winner1 = dc.document["winner1"] as String,
                                           winner2 = dc.document["winner2"] as String,
                                           winner3 = dc.document["winner3"] as String

                                       ))

                                        Log.d("sports3", "Modified city: ${dc.document.data}")
                                    }

                                    DocumentChange.Type.REMOVED ->
                                    {
                                        Log.d("sports4", "Removed city: ${dc.document.data}")
                                        eventsDao.deleteSportsData(dc.document.id.toInt())
                                    }

                                }
                            }
                             saveSportsDataRoom(sportsData).subscribe()
                            Log.d("sports2", "added sports dta a: $sportsData")
                    }

                }

    @SuppressLint("CheckResult")
    private fun saveSportsDataRoom(sportsData: List<SportsData>): Completable {
        Log.d("sports2", "added sports dta a: $sportsData")
        return eventsDao.saveSportsData(sportsData).subscribeOn(Schedulers.io())
            .doOnComplete {
                Log.d("Sports", "Data Saved")
            }
            .doOnError {
                Log.d("Sports","Data Not Saved$it")
            }
    }

    fun updateFavourite(eventId: String, favouriteMark: Int): Completable {
        return eventsDao.updateMiscFavourite(id = eventId, mark = favouriteMark).subscribeOn(Schedulers.io())
    }
}





