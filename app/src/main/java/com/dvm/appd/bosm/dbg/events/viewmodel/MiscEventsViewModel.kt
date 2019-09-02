package com.dvm.appd.bosm.dbg.events.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import io.reactivex.android.schedulers.AndroidSchedulers

class MiscEventsViewModel(val eventsRepository: EventsRepository): ViewModel() {

    var miscEvents: LiveData<List<MiscEventsData>> = MutableLiveData()
    var eventDays: LiveData<List<String>> = MutableLiveData()
    var daySelected: LiveData<String> = MutableLiveData()

    init {

        eventsRepository.miscEventDays()
            .doOnNext {
                Log.d("MiscEventVM", it.toString())
                (eventDays as MutableLiveData).postValue(it)
            }
            .doOnError {
                Log.d("MiscEventVM", it.toString())
            }
            .subscribe()

    }

    fun markEventFavourite(eventId: String, favouriteMark: Int){
        eventsRepository.updateFavourite(eventId, favouriteMark).subscribe()
    }

    fun getMiscEventsData(day: String){
        eventsRepository.getDayMiscEvents(day)
            .doOnNext {
                Log.d("MiscEventVM", it.toString())
                (miscEvents as MutableLiveData).postValue(it)
            }
            .doOnError {
                Log.d("MiscEventVM", it.toString())
            }
            .subscribe()
    }
}