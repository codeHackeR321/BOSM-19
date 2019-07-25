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
import java.util.ArrayList

class EventsRepository (val eventsDao: EventsDao){

    init {

        val db = FirebaseFirestore.getInstance()

        // Get sports name form firestore
        db.collection("Events").document("Sports")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {

                    var names: MutableList<SportsNamesData> = arrayListOf()

                    snapshot.data!!.values.forEach {
                        names.add(SportsNamesData(it.toString()))
                    }

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
        db.collection("Events").document("Misc").collection("Day 1")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null){
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null){

                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    snapshot.documents.forEach {
                        miscEvents.add(MiscEventsData(name = (it.get("Name") as String)
                            , location = (it.get("Location") as String), time = (it.get("Time") as String)
                            , description = (it.get("Description") as String), day = "Day 1"))
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
        db.collection("Events").document("Misc").collection("Day 2")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null){
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null){

                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    snapshot.documents.forEach {
                        miscEvents.add(MiscEventsData(name = (it.get("Name") as String)
                            , location = (it.get("Location") as String), time = (it.get("Time") as String)
                            , description = (it.get("Description") as String), day = "Day 2"))
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


//        //Get misc events data for day 3 form firestore
//        db.collection("Events").document("Misc").collection("Day 3")
//            .addSnapshotListener { snapshot, exception ->
//
//                if (exception != null){
//                    Log.e("EventsRepo", "Listen failed", exception)
//                    return@addSnapshotListener
//                }
//                if (snapshot != null){
//
//                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()
//
//                    snapshot.documents.forEach {
//                        miscEvents.add(MiscEventsData(name = (it.get("Name") as String)
//                            , location = (it.get("Location") as String), time = (it.get("Time") as String)
//                            , description = (it.get("Description") as String), day = (it.id)))
//                    }
//
//                    eventsDao.insertMiscEventData(miscEvents).subscribeOn(Schedulers.io())
//                        .subscribe(object : CompletableObserver{
//                            override fun onComplete() {
//                            }
//
//                            override fun onSubscribe(d: Disposable) {
//                            }
//
//                            override fun onError(e: Throwable) {
//                            }
//                        })
//                }
//
//            }
//
//        //Get misc events data for day 4 form firestore
//        db.collection("Events").document("Misc").collection("Day 4")
//            .addSnapshotListener { snapshot, exception ->
//
//                if (exception != null){
//                    Log.e("EventsRepo", "Listen failed", exception)
//                    return@addSnapshotListener
//                }
//                if (snapshot != null){
//
//                    val miscEvents: MutableList<MiscEventsData> = arrayListOf()
//
//                    snapshot.documents.forEach {
//                        miscEvents.add(MiscEventsData(name = (it.get("Name") as String)
//                            , location = (it.get("Location") as String), time = (it.get("Time") as String)
//                            , description = (it.get("Description") as String), day = (it.id)))
//                    }
//
//                    eventsDao.insertMiscEventData(miscEvents).subscribeOn(Schedulers.io())
//                        .subscribe(object : CompletableObserver{
//                            override fun onComplete() {
//                            }
//
//                            override fun onSubscribe(d: Disposable) {
//                            }
//
//                            override fun onError(e: Throwable) {
//                            }
//                        })
//                }
//
//            }
    }

    fun getSportsName(): Flowable<List<SportsNamesData>>{
        return eventsDao.getSportsName().subscribeOn(Schedulers.io())
    }

    fun getMiscEvents(): Flowable<List<MiscEventsData>>{
        return eventsDao.getMiscEvents().subscribeOn(Schedulers.io())
    }

}
