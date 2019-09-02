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


class EventsRepository(val eventsDao: EventsDao) {

    val db = FirebaseFirestore.getInstance()

    init {

        getSportsDataFromFirestore()

        db.collection("events").document("misc").collection("eventdata")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    Log.e("EventsRepo", "Listen failed", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null) {

                    var miscEvents: MutableList<MiscEventsData> = arrayListOf()

                    for (doc in snapshot.documentChanges) {

                        var name: String
                        var venue: String
                        var time: String
                        var description: String
                        var day: String
                        var organiser: String

                        when (doc.type) {

                            DocumentChange.Type.ADDED -> {
                                Log.d("DocAdded", doc.document.get("name") as String)

                                name = try {
                                    doc.document["name"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                venue = try {
                                    doc.document["venue"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                time = try {
                                    doc.document["timestamp"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                description = try {
                                    doc.document["description"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                day = try {
                                    doc.document["day"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                organiser = try {
                                    doc.document["organiser"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }

                                miscEvents.add(
                                    MiscEventsData(
                                        id = doc.document.id,
                                        name = name,
                                        venue = venue,
                                        time = time,
                                        description = description,
                                        day = day,
                                        organiser = organiser,
                                        isFavourite = 0
                                    )
                                )
                            }

                            DocumentChange.Type.MODIFIED -> {
                                Log.d("DocChanged", doc.document.get("name") as String)

                                name = try {
                                    doc.document["name"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                venue = try {
                                    doc.document["venue"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                time = try {
                                    doc.document["timestamp"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                description = try {
                                    doc.document["description"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                day = try {
                                    doc.document["day"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }
                                organiser = try {
                                    doc.document["organiser"] as String
                                } catch (e: Exception) {
                                    "Not Available"
                                }

                                eventsDao.updateMiscData(
                                    id = doc.document.id,
                                    name = name,
                                    venue = venue,
                                    time = time,
                                    description = description,
                                    day = day,
                                    organiser = organiser
                                )
                                    .subscribeOn(Schedulers.io())
                                    .doOnError {
                                        Log.e("EventsRepo", it.message, it)
                                    }
                                    .subscribe()
                            }

                            DocumentChange.Type.REMOVED -> {
                                Log.d("DocRemoved", doc.document.get("name") as String)
                                eventsDao.deleteMiscEvent(doc.document.id)
                                    .subscribeOn(Schedulers.io())
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

    fun getSportsName(): Flowable<List<String>> {
        return eventsDao.getSportsName().subscribeOn(Schedulers.io())

    }

    fun miscEventDays(): Flowable<List<String>> {
        return eventsDao.getMiscDays().subscribeOn(Schedulers.io())
    }

    fun getDayMiscEvents(day: String): Flowable<List<MiscEventsData>> {
        return eventsDao.getDayMiscEvents(day).subscribeOn(Schedulers.io())
    }

    fun getSportData(name: String): Single<List<SportsData>> {
        return eventsDao.getSportDataForSport(name).subscribeOn(Schedulers.io())
    }

    fun getGenderForSport(name: String): Single<List<String>> {
        return eventsDao.getDistinctGenderForSport(name).subscribeOn(Schedulers.io())
    }

    private fun getSportsDataFromFirestore() {

        var sportsData: MutableList<SportsData> = arrayListOf()
        db.collection("events").document("sports").collection("matches")
            .addSnapshotListener { snapshots, e ->

                if (e != null) {
                    Log.w("Sports", "listen:error", e)
                    return@addSnapshotListener
                }

                var match_no: Int
                var name: String
                var round: String
                var round_type: String
                var team_1: String
                var team_2: String
                var time_stamp: String
                var venue: String
                var gender: String
                var isScore: Boolean
                var layout: Int
                var score_1: String
                var score_2: String
                var winner1: String
                var winner2: String
                var winner3: String

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            Log.d("sports1", "added doc data : ${dc.document.data}")

                            match_no = try {
                                dc.document.id.toInt()
                            } catch (e: Exception) {
                                0
                            }
                            name = try {
                                dc.document["sport"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            round = try {
                                dc.document["round_name"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            round_type = try {
                                dc.document["round_type"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            team_1 = try {
                                dc.document["team1"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            team_2 = try {
                                dc.document["team2"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            time_stamp = try {
                                dc.document["timestamp"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            venue = try {
                                dc.document["venue"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            //TODO Check default Value
                            gender = try {
                                dc.document["gender"] as String
                            } catch (e: Exception) {
                                "Boys"
                            }
                            isScore = try {
                                dc.document["is_score"] as Boolean
                            } catch (e: Exception) {
                                false
                            }
                            layout = try {
                                (dc.document["layout"] as Long).toInt()
                            } catch (e: Exception) {
                                2
                            }
                            score_1 = try {
                                dc.document["score1"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            score_2 = try {
                                dc.document["score2"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            winner1 = try {
                                dc.document["winner1"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            winner2 = try {
                                dc.document["winner2"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            winner3 = try {
                                dc.document["winner3"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }

                            sportsData.add(
                                SportsData(
                                    match_no = match_no,
                                    name = name,
                                    round = round,
                                    round_type = round_type,
                                    team_1 = team_1,
                                    team_2 = team_2,
                                    time = time_stamp,
                                    venue = venue,
                                    gender = gender,
                                    isScore = isScore,
                                    layout = layout,
                                    score_1 = score_1,
                                    score_2 = score_2,
                                    winner1 = winner1,
                                    winner2 = winner2,
                                    winner3 = winner3

                                )
                            )

                        }

                        DocumentChange.Type.MODIFIED -> {

                            match_no = try {
                                dc.document.id.toInt()
                            } catch (e: Exception) {
                                0
                            }
                            name = try {
                                dc.document["sport"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            round = try {
                                dc.document["round_name"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            round_type = try {
                                dc.document["round_type"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            team_1 = try {
                                dc.document["team1"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            team_2 = try {
                                dc.document["team2"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            time_stamp = try {
                                dc.document["timestamp"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            venue = try {
                                dc.document["venue"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            //TODO Check default Value
                            gender = try {
                                dc.document["gender"] as String
                            } catch (e: Exception) {
                                "Boys"
                            }
                            isScore = try {
                                dc.document["is_score"] as Boolean
                            } catch (e: Exception) {
                                false
                            }
                            layout = try {
                                (dc.document["layout"] as Long).toInt()
                            } catch (e: Exception) {
                                2
                            }
                            score_1 = try {
                                dc.document["score1"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            score_2 = try {
                                dc.document["score2"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            winner1 = try {
                                dc.document["winner1"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            winner2 = try {
                                dc.document["winner2"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }
                            winner3 = try {
                                dc.document["winner3"] as String
                            } catch (e: Exception) {
                                "Not Available"
                            }

                            try {
                                var position =
                                    sportsData.indexOfFirst { it.match_no == dc.document.id.toInt() }

                                sportsData.set(
                                    position, SportsData(
                                        match_no = match_no,
                                        name = name,
                                        round = round,
                                        round_type = round_type,
                                        team_1 = team_1,
                                        team_2 = team_2,
                                        time = time_stamp,
                                        venue = venue,
                                        gender = gender,
                                        isScore = isScore,
                                        layout = layout,
                                        score_1 = score_1,
                                        score_2 = score_2,
                                        winner1 = winner1,
                                        winner2 = winner2,
                                        winner3 = winner3

                                    )
                                )
                            } catch (e: Exception) {
                                // TODO Do something with this exception
                                // TODO Add crashanalytic log for this
                            }
                            Log.d("sports3", "Modified city: ${dc.document.data}")
                        }

                        DocumentChange.Type.REMOVED -> {
                            Log.d("sports4", "Removed city: ${dc.document.data}")
                            eventsDao.deleteSportsData(dc.document.id.toInt())
                        }
                    }
                }
                saveSportsDataRoom(sportsData).subscribe()
                Log.d("sports2", "added sports data a: $sportsData")
            }
    }

    @SuppressLint("CheckResult")
    private fun saveSportsDataRoom(sportsData: List<SportsData>): Completable {
        Log.d("sports2", "added sports data a: $sportsData")
        return eventsDao.saveSportsData(sportsData).subscribeOn(Schedulers.io())
            .doOnComplete {
                Log.d("Sports", "Data Saved")
            }
            .doOnError {
                Log.d("Sports", "Data Not Saved$it")
            }
    }

    fun updateFavourite(eventId: String, favouriteMark: Int): Completable {
        return eventsDao.updateMiscFavourite(id = eventId, mark = favouriteMark)
            .subscribeOn(Schedulers.io())
    }
}





