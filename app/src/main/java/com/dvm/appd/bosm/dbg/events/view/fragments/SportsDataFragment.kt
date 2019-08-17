package com.dvm.appd.bosm.dbg.events.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.view.adapters.GenderDataAdapter
import com.dvm.appd.bosm.dbg.events.view.adapters.SportsDataAdapter
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModel
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModelFactory
import kotlinx.android.synthetic.main.fragment_sports_data.view.*

class SportsDataFragment() : Fragment() {

    var sportName= ""


    private lateinit var sportsDataViewModel: SportsDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        sportsDataViewModel = ViewModelProviders.of(this, SportsDataViewModelFactory(sportName))[SportsDataViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_sports_data, container, false)

        view.recy_sports_horizontal.adapter = GenderDataAdapter()
        sportsDataViewModel.gender.observe(this, Observer {

            Log.d("SportsFRag", "Observed")
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).gender = it
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).notifyDataSetChanged()
        })
        view.recy_sports_vertical.adapter = SportsDataAdapter()
        sportsDataViewModel.sportsData.observe(this, Observer {

            Log.d("SportsFRag", "Observed")
            (view.recy_sports_vertical.adapter as SportsDataAdapter).sportData = it
            (view.recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
        })
        return view
    }

   /* override fun updateIsFavourite(eventId: String, favouriteMark: Int) {
        miscEventsViewViewModel.markEventFavourite(eventId, favouriteMark)
    }*/
}
