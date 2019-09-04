package com.dvm.appd.bosm.dbg.events.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData

class SportsDataViewModel(val eventsRepository: EventsRepository, private var name: String) : ViewModel() {

    var sportsData: LiveData<Map<String,List<SportsData>>> = MutableLiveData()
    var gender: LiveData<List<String>> = MutableLiveData()

    init {
        data()
    }

    @SuppressLint("CheckResult")
    private fun data() {
        eventsRepository.getGenderForSport(name).subscribe({
            Log.d("Sports6","Sucess getting gender room")
            (gender as MutableLiveData).postValue(it)

        },{
            Log.d("Sports5","Error getting gender room")
        })

        eventsRepository.getSportData(name).subscribe({
            Log.d("Sports6","Sucess getting sports data  room")
            (sportsData as MutableLiveData).postValue(it.groupBy { it.gender})

        },{
            Log.d("Sports5","Error getting sportdata room")
        })
    }

    fun markMatchFavourite(matchNo: Int, favouriteMark: Int){
        eventsRepository.updateSportsFavourite(matchNo, favouriteMark).subscribe()
    }
}