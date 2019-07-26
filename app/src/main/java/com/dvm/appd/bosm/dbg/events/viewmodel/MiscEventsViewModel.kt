package com.dvm.appd.bosm.dbg.events.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import io.reactivex.android.schedulers.AndroidSchedulers

class MiscEventsViewModel(eventsRepository: EventsRepository): ViewModel() {

    var miscEvents: LiveData<List<MiscEventsData>> = MutableLiveData()

    init {

        eventsRepository.getMiscEvents()
        .doOnNext {
            Log.d("EventRepo", it.toString())
            (miscEvents as MutableLiveData).postValue(it)
        }
        .doOnError {
            Log.d("EventRepo", it.toString())
        }
        .subscribe()

    }
}