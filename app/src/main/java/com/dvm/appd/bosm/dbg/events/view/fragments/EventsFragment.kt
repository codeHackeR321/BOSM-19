package com.dvm.appd.bosm.dbg.events.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.view.adapters.EventsAdapter
import com.dvm.appd.bosm.dbg.events.viewmodel.EventsViewModel
import com.dvm.appd.bosm.dbg.events.viewmodel.EventsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_events_fragment.view.*

class EventsFragment : Fragment(), EventsAdapter.OnSportsNameClicked{

    private lateinit var eventsViewViewModel: EventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        eventsViewViewModel = ViewModelProviders.of(this, EventsViewModelFactory())[EventsViewModel::class.java]

        val view = inflater.inflate(R.layout.fra_events_fragment, container, false)

        activity!!.mainView.setBackgroundResource(R.drawable.events_title)
        activity!!.fragmentName.text = "Events"

        activity!!.cart.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_events_to_action_cart)
        }

        activity!!.profile.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_events_to_action_profile)
        }

        activity!!.notifications.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_events_to_notificationFragment)
        }

        view.eventsRecycler.adapter = EventsAdapter(this)

        eventsViewViewModel.sportsName.observe(this, Observer {

            Log.d("EventsFrag", "Observed $it")
            (view.eventsRecycler.adapter as EventsAdapter).sportsName = it
            (view.eventsRecycler.adapter as EventsAdapter).notifyDataSetChanged()
        })

        view.miscEvents.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_action_events_to_miscEventsFragment, null))

     return view
     }

    override fun openSportsFragment(name: String) {
        val bundle = bundleOf("name" to name)
        view!!.findNavController().navigate(R.id.sportsDataFragment, bundle)


        //put navigation code here with name passed in openSportsFragment
    }
}