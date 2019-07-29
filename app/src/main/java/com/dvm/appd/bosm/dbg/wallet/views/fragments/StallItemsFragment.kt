package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallItemsViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallItemsViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.StallItemsAdapter
import kotlinx.android.synthetic.main.fra_wallet_stall_items.*
import kotlinx.android.synthetic.main.fra_wallet_stall_items.view.*

class StallItemsFragment : Fragment(), StallItemsAdapter.OnAddClickedListener {
    private lateinit var stallItemsViewModel: StallItemsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val stallId = arguments?.getInt("stallId")
        val rootView = inflater.inflate(R.layout.fra_wallet_stall_items, container, false)
        stallItemsViewModel = ViewModelProviders.of(this, StallItemsViewModelFactory(stallId!!))[StallItemsViewModel::class.java]

        rootView.items_recycler.adapter = StallItemsAdapter(this)
        stallItemsViewModel.items.observe(this, Observer {
            (rootView.items_recycler.adapter as StallItemsAdapter).stallItems = it
            (rootView.items_recycler.adapter as StallItemsAdapter).notifyDataSetChanged()
        })
        return rootView
    }

    override fun addButtonClicked(itemId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}