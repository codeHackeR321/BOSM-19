package com.dvm.appd.bosm.dbg.events.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.view.adapters.MiscEventsAdapter
import com.dvm.appd.bosm.dbg.events.viewmodel.MiscEventsViewModel
import com.dvm.appd.bosm.dbg.events.viewmodel.MiscEventsViewModelFactory
import kotlinx.android.synthetic.main.fra_misc_events.view.*

class MiscEventsFragment : Fragment(), MiscEventsAdapter.OnMarkFavouriteClicked {

    private lateinit var miscEventsViewViewModel: MiscEventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        miscEventsViewViewModel = ViewModelProviders.of(this, MiscEventsViewModelFactory())[MiscEventsViewModel::class.java]

        val view = inflater.inflate(R.layout.fra_misc_events, container, false)

        view.miscEventRecycler.adapter = MiscEventsAdapter(this)
        miscEventsViewViewModel.miscEvents.observe(this, Observer {

            Log.d("MiscEventsFrag", "Observed")
            (view.miscEventRecycler.adapter as MiscEventsAdapter).miscEvents = it
            (view.miscEventRecycler.adapter as MiscEventsAdapter).notifyDataSetChanged()
        })
        return view
    }

    override fun updateIsFavourite(eventId: String, favouriteMark: Int) {
        miscEventsViewViewModel.markEventFavourite(eventId, favouriteMark)
    }
}
