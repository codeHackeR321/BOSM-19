package com.dvm.appd.bosm.dbg.events.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData

class SportsDataViewModel(val eventsRepository: EventsRepository, private var name: String) : ViewModel() {

    var sportsData: LiveData<List<SportsData>> = MutableLiveData()
    var gender: LiveData<List<String>> = MutableLiveData()

    init {
        data()
    }

    @SuppressLint("CheckResult")
    private fun data() {
        eventsRepository.getGenderForSport(name).subscribe({
            Log.d("Sports6","Sucess getting gender room")
            (gender as MutableLiveData).postValue(it)
            eventsRepository.getSportData(name).subscribe({
                Log.d("Sports6","Sucess getting gender room")
                (sportsData as MutableLiveData).postValue(it)

            },{
                Log.d("Sports5","Error getting sportdata room")
            })
        },{

            Log.d("Sports5","Error getting gender room")
        })
    }

    /*fun markMatchFavourite(eventId: String, favouriteMark: Int){
        eventsRepository.updateFavourite(eventId, favouriteMark).subscribe()
    }*/
}