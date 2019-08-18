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
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository.Keys.name
import com.dvm.appd.bosm.dbg.events.view.adapters.GenderDataAdapter
import com.dvm.appd.bosm.dbg.events.view.adapters.SportsDataAdapter
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModel
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModelFactory
import kotlinx.android.synthetic.main.fragment_sports_data.*
import kotlinx.android.synthetic.main.fragment_sports_data.view.*
import kotlinx.android.synthetic.main.fragment_sports_data.view.recy_sports_vertical

class SportsDataFragment() : Fragment(),GenderDataAdapter.OnGenderClicked {


    // to be set by Suyash


    private lateinit var sportsDataViewModel: SportsDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var sportName= arguments?.getString("name")
        Log.d("Sports", "sports name selected: $name")
        sportsDataViewModel = ViewModelProviders.of(this, SportsDataViewModelFactory(sportName!!))[SportsDataViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_sports_data, container, false)

        view.textView4.text=sportName.capitalize()

        view.recy_sports_horizontal.adapter = GenderDataAdapter(this)
        sportsDataViewModel.gender.observe(this, Observer {

            Log.d("SportsFRag", "Observed")
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).gender = it
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).genderSelected = it[0]
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).notifyDataSetChanged()

            (view.recy_sports_vertical.adapter as SportsDataAdapter).genderSelected=it[0]
            (view.recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()

        })

        view.recy_sports_vertical.adapter = SportsDataAdapter()
        sportsDataViewModel.sportsData.observe(this, Observer {

            Log.d("SportsFRag", "Observed")
            (view.recy_sports_vertical.adapter as SportsDataAdapter).sportData = it
            (view.recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
        })
        return view
    }

    override fun genderClicked(gender: String) {
        (recy_sports_vertical.adapter as SportsDataAdapter).genderSelected=gender
        (recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
    }
}
