package com.dvm.appd.bosm.dbg.events.data.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.events.data.room.EventsDao
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import com.google.firebase.Timestamp
import java.util.ArrayList

class EventsRepository (val eventsDao: EventsDao){

    init {

        val db = FirebaseFirestore.getInstance()

        // Get sports name form firestore
        db.collection("events").document("sports")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    Log.e("Events", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {

                    var names: MutableList<SportsNamesData> = arrayListOf()

                    snapshot.data!!.keys.forEach {
                        names.add(SportsNamesData(it, snapshot.data!!.getValue(it) as String))
                    }

                    Log.d("Events", "$names")
                    eventsDao.insertSportsName(names).subscribeOn(Schedulers.io())
                        .subscribe(object : CompletableObserver{
                            override fun onComplete() {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onSubscribe(d: Disposable) {
                                Log.d("EventsRepo", "Subscribed")
                            }

                            override fun onError(e: Throwable) {
                                Log.d("EventsRepo", "Error", e)
                            }
                        })
                }
            }


        //Get misc events data for day 1 form firestore
        db.collection("events").document("misc").collection("day1")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null){
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null){

                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    snapshot.documents.forEach {
                        miscEvents.add(MiscEventsData(name = (it.get("name") as String)
                            , venue = (it.get("venue") as String), time = ((it.get("timestamp") as Timestamp)).toString()
                            , description = (it.get("description") as String), day = "Day 1"
                            , organiser = (it.get("organiser") as String)))
                    }

                    Log.d("Events", miscEvents.toString())
                    eventsDao.insertMiscEventData(miscEvents).subscribeOn(Schedulers.io())
                        .subscribe(object : CompletableObserver{
                            override fun onComplete() {
                            }

                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onError(e: Throwable) {
                            }
                        })
                }
            }


        //Get misc events data for day 2 form firestore
        db.collection("events").document("misc").collection("day2")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null){
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null){

                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    snapshot.documents.forEach {
                        miscEvents.add(MiscEventsData(name = (it.get("name") as String)
                            , venue = (it.get("venue") as String), time = ((it.get("timestamp") as Timestamp)).toString()
                            , description = (it.get("description") as String), day = "Day 2"
                            , organiser = (it.get("organiser") as String)))
                    }

                    Log.d("Events", miscEvents.toString())
                    eventsDao.insertMiscEventData(miscEvents).subscribeOn(Schedulers.io())
                        .subscribe(object : CompletableObserver{
                            override fun onComplete() {
                            }

                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onError(e: Throwable) {
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

}
