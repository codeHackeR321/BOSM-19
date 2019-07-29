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
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange

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
                                Log.e("EventsRepo", "Error", e)
                            }
                        })
                }
            }


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

//                    snapshot.documents.forEach {
//                        miscEvents.add(MiscEventsData(id = it.id, name = (it.get("name") as String)
//                            , venue = (it.get("venue") as String), time = ((it.get("timestamp") as Timestamp)).toString()
//                            , description = (it.get("description") as String), day = (it.get("day") as String)
//                            , organiser = (it.get("organiser") as String), isFavourite = 0))
//                    }

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

}
