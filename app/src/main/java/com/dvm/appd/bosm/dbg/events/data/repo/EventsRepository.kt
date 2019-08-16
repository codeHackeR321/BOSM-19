package com.dvm.appd.bosm.dbg.events.data.repo

import android.util.Log
import com.dvm.appd.bosm.dbg.events.data.room.EventsDao
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import java.util.ArrayList

class EventsRepository (val eventsDao: EventsDao){

    val db = FirebaseFirestore.getInstance()
    init {


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
                    //get data for all sports
                    getSportsDataFromFirestore(names)

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

    private fun getSportsDataFromFirestore(names: MutableList<SportsNamesData>){
        for(name in names){

            db.collection("events").document("sports").collection(name.name).get()
                .addOnSuccessListener { docs ->
                    val sportsData: MutableList<SportsData> = arrayListOf()
                    for (doc in docs) {
                          doc.reference.collection("matches").addSnapshotListener{snapshots , e ->

                              if (e != null) {
                                  Log.w("Sports", "listen:error", e)
                                  return@addSnapshotListener
                              }

                              for (dc in snapshots!!.documentChanges) {
                                  when (dc.type) {
                                      DocumentChange.Type.ADDED -> {
                                          Log.d("sports1", "added doc data : ${dc.document.data}")




                                              sportsData.add(
                                                  SportsData(match_no = dc.document.id,
                                                      description = dc.document["description"] as String,
                                                      name = name.name,result = dc.document["result"] as String,
                                                      round = dc.document["round"] as String,
                                                      round_type = dc.document["roundtype"] as String,
                                                      team_1 = dc.document["team1"] as String,
                                                      team_2 = dc.document["team2"] as String,
                                                      time = (dc.document["timestamp"] as Timestamp).toString(),
                                                      venue = dc.document["venue"] as String

                                                      )
                                              )




                                      }
                                      DocumentChange.Type.MODIFIED ->
                                          Log.d("sports3", "Modified city: ${dc.document.data}")
                                      DocumentChange.Type.REMOVED ->
                                          Log.d("sports4", "Removed city: ${dc.document.data}")

                                  }
                              }
                          }
                    }

                    Log.d("sports2", "added sports dta a: ${sportsData}")
                }
                .addOnFailureListener { exception ->
                    Log.d("sports5", "Error getting documents: ", exception)
                }

            }

        }

    }


