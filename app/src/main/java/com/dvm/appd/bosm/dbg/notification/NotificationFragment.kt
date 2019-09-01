package com.dvm.appd.bosm.dbg.notification


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notification.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    private val notificationViewModel by lazy {
        ViewModelProviders.of(this, NotificationViewModelFactory())[NotificationViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationViewModel.readNotificationsFromRoom()

        activity!!.fragmentName.isVisible = false
        activity!!.cart.isVisible = false
        activity!!.profile.isVisible = false
        activity!!.notifications.isVisible = false
        activity!!.bottom_navigation_bar.isVisible = false
        activity!!.mainView.visibility = View.GONE
        activity!!.fragmentName.visibility = View.GONE

        notificationViewModel.error.observe(this, Observer {
            if (it != null) {
                Log.e("Notification", "Error in reading messages")
                // TODO show alert dialog box
            }
        })

        notificationViewModel.isLoading.observe(this, Observer {
            if (it)
                progressBar_Notification.visibility = View.VISIBLE
            else
                progressBar_Notification.visibility = View.INVISIBLE
        })

        notificationRecycler.adapter = NotificationAdapter()

        notificationViewModel.notifications.observe(this, Observer {
            Log.d("Notification", "List Observed = ${it[0].toString()}")
            (notificationRecycler.adapter as NotificationAdapter).notifications = it
            (notificationRecycler.adapter as NotificationAdapter).notifyDataSetChanged()
        })

        backBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDetach() {
        activity!!.mainView.isVisible = true
        activity!!.fragmentName.isVisible = true
        activity!!.cart.isVisible = true
        activity!!.profile.isVisible = true
        activity!!.notifications.isVisible = true
        activity!!.bottom_navigation_bar.isVisible = true
        super.onDetach()
    }

}
