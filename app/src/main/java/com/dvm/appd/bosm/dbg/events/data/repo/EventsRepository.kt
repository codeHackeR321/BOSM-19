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

                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    for (doc in snapshot.documentChanges){

                        when(doc.type){
                            DocumentChange.Type.ADDED -> {
                                Log.d("DocAdded", doc.document.get("name") as String)
                                miscEvents.add(MiscEventsData(id = doc.document.id, name = (doc.document.get("name")) as String
                                , venue = (doc.document.get("venue") as String), time = ((doc.document.get("timestamp") as Timestamp)).toString()
                                , description = (doc.document.get("description") as String), day = (doc.document.get("day") as String)
                                , organiser = (doc.document.get("organiser") as String), isFavourite = 0))
                            }

                            DocumentChange.Type.MODIFIED -> {
                                Log.d("DocChanged", doc.document.get("name") as String)
                                eventsDao.updateMiscData(id = doc.document.id, name = (doc.document.get("name")) as String
                                    , venue = (doc.document.get("venue") as String), time = ((doc.document.get("timestamp") as Timestamp)).toString()
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
                        .subscribe(object : CompletableObserver{
                            override fun onComplete() {
                            }

                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onError(e: Throwable) {
                                Log.e("EventsRepo", e.message, e)
                            }
                        })
                }
            }

    }

    fun getSportsName(): Flowable<List<SportsNamesData>>{
        return eventsDao.getSportsName().subscribeOn(Schedulers.io())

    }

    fun getMiscEvents(): Flowable<List<MiscEventsData>>{
        return eventsDao.getMiscEvents().subscribeOn(Schedulers.io())
    }

    fun getSportData(name:String): Single<List<SportsData>> {
        return eventsDao.getSportDataForSport(name).subscribeOn(Schedulers.io())
    }

    fun getGenderForSport(name: String):Single<List<String>>{

        return eventsDao.getDistinctGenderForSport(name).subscribeOn(Schedulers.io())
    }


    private fun getSportsDataFromFirestore(){

        var sportsData= mutableListOf<SportsData>()
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
                                                round = dc.document["round"] as String,
                                                round_type = dc.document["roundtype"] as String,
                                                team_1 = dc.document["team1"] as String,
                                                team_2 = dc.document["team2"] as String,
                                                time = (dc.document["timestamp"] as Timestamp).seconds,
                                                venue = dc.document["venue"] as String,
                                                gender = dc.document["gender"] as String,
                                                isScore = dc.document["isscore"] as Boolean,
                                                layout = dc.document["layout"] as Int,
                                                score_1 = dc.document["score1"] as String,
                                                score_2 = dc.document["score2"] as String,
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
                                           layout = dc.document["layout"] as Int,
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


                    }
                    saveSportsDataRoom(sportsData)
                    Log.d("sports2", "added sports dta a: ${sportsData}")
                }





    @SuppressLint("CheckResult")
    private fun saveSportsDataRoom(sportsData: MutableList<SportsData>) {
eventsDao.saveSportsData(sportsData).subscribeOn(Schedulers.io()).subscribe({
    Log.d("Sports","Data Saved")
},{

    Log.d("Sports","Data Not Saved")
})
    }

    fun updateFavourite(eventId: String, favouriteMark: Int): Completable {
        return eventsDao.updateMiscFavourite(id = eventId, mark = favouriteMark).subscribeOn(Schedulers.io())
    }
}





