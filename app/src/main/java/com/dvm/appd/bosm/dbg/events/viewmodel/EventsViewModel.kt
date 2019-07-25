package com.dvm.appd.bosm.dbg.events.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import java.lang.Exception

class EventsViewModel(eventsRepository: EventsRepository): ViewModel() {

    var sportsName: LiveData<List<SportsNamesData>> = MutableLiveData()

    init {

        eventsRepository.getSportsName().subscribe({
            Log.d("EventRepo", "$it")
            (sportsName as MutableLiveData).postValue(it)
        },
        {
            Log.e("EventRepo", "Error : ${it.message}")
        })
        .dispose()

    }
}