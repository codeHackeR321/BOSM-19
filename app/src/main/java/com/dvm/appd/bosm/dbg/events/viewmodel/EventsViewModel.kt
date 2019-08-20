package com.dvm.appd.bosm.dbg.events.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData

class EventsViewModel(val eventsRepository: EventsRepository): ViewModel() {

    var sportsName: LiveData<List<String>> = MutableLiveData()

    init {

        eventsRepository.getSportsName()
        .doOnNext {
            Log.d("EventRepo", it.toString())
            (sportsName as MutableLiveData).postValue(it)
        }
        .doOnError {
            Log.d("EventRepo", it.toString())
        }
        .subscribe()

    }
}