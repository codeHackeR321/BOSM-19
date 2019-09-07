package com.dvm.appd.bosm.dbg.more

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : Fragment(), MoreAdapter.onMoreItemClicked {

    var moreItems = listOf("Contact Us", "Developers", "Map", "EPC Blog", "HPC Blog", "Picture Gallery", "Sponsors")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.fragmentName.text = resources.getString(R.string.action_more)

        recycler_card_more.adapter = MoreAdapter(this)
        (recycler_card_more.adapter as MoreAdapter).moreItems = moreItems
        (recycler_card_more.adapter as MoreAdapter).notifyDataSetChanged()

        activity!!.cart.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_more_to_action_cart)
        }

        activity!!.profile.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_more_to_action_profile)
        }

        activity!!.notifications.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_more_to_notificationFragment)
        }
    }

    override fun moreButtonClicked(item: Int) {
        Toast.makeText(context, "Position clicked = $item.", Toast.LENGTH_LONG).show()
        when(item) {
            0 -> {
                val bundle = bundleOf("title" to "Contact Us")
                view!!.findNavController().navigate(R.id.action_action_more_to_fragmentRecyclerView, bundle)
            }
            1 -> {
                val bundle = bundleOf("title" to "Developers")
                view!!.findNavController().navigate(R.id.action_action_more_to_fragmentRecyclerView, bundle)
            }
            2 -> {
                view!!.findNavController().navigate(R.id.action_action_more_to_mapFragment)
            }
            3 -> {
                val bundle = bundleOf("link" to view!!.resources.getString(R.string.link_EPC), "title" to "EPC Blog")
                view!!.findNavController().navigate(R.id.action_action_more_to_fragmentWebPage, bundle)
            }
            4 -> {
                val bundle = bundleOf("link" to view!!.resources.getString(R.string.link_HPC), "title" to "HPC Blog")
                view!!.findNavController().navigate(R.id.action_action_more_to_fragmentWebPage, bundle)
            }
            6 -> {
                val bundle = bundleOf("link" to view!!.resources.getString(R.string.link_Sponsors), "title" to "Sponsors")
                view!!.findNavController().navigate(R.id.action_action_more_to_fragmentWebPage, bundle)
            }
        }
    }

    override fun onResume() {
        activity!!.mainView.isVisible = true
        activity!!.fragmentName.isVisible = true
        activity!!.cart.isVisible = true
        activity!!.profile.isVisible = true
        activity!!.notifications.isVisible = true
        activity!!.bottom_navigation_bar.isVisible = true
        super.onResume()
    }
}
