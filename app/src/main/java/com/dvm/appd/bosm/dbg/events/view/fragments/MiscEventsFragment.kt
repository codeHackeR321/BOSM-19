package com.dvm.appd.bosm.dbg.events.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.view.adapters.MiscDayAdapter
import com.dvm.appd.bosm.dbg.events.view.adapters.MiscEventsAdapter
import com.dvm.appd.bosm.dbg.events.viewmodel.MiscEventsViewModel
import com.dvm.appd.bosm.dbg.events.viewmodel.MiscEventsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_misc_events.view.*

class MiscEventsFragment : Fragment(), MiscEventsAdapter.OnMarkFavouriteClicked {

    private lateinit var miscEventsViewViewModel: MiscEventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        miscEventsViewViewModel = ViewModelProviders.of(this, MiscEventsViewModelFactory())[MiscEventsViewModel::class.java]

        val view = inflater.inflate(R.layout.fra_misc_events, container, false)

        activity!!.my_toolbar.isVisible = false

        view.dayRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.dayRecycler.adapter = MiscDayAdapter()

        view.miscEventRecycler.adapter = MiscEventsAdapter(this)
        miscEventsViewViewModel.miscEvents.observe(this, Observer {

            Log.d("MiscEventsFrag", "Observed")
            (view.miscEventRecycler.adapter as MiscEventsAdapter).miscEvents = it
            (view.miscEventRecycler.adapter as MiscEventsAdapter).notifyDataSetChanged()
        })
        return view
    }

    override fun onDetach() {
        super.onDetach()

        activity!!.my_toolbar.isVisible = true
    }

    override fun updateIsFavourite(eventId: String, favouriteMark: Int) {
        miscEventsViewViewModel.markEventFavourite(eventId, favouriteMark)
    }
}
