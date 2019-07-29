package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallsViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallsViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.StallsAdapter
import kotlinx.android.synthetic.main.fra_wallet_stalls.*
import kotlinx.android.synthetic.main.fra_wallet_stalls.view.*

class StallsFragment : Fragment(), StallsAdapter.OnStallSelectedListener {

    private lateinit var stallsViewModel: StallsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        stallsViewModel = ViewModelProviders.of(this, StallsViewModelFactory())[StallsViewModel::class.java]

        val rootview = inflater.inflate(R.layout.fra_wallet_stalls, container, false)

        rootview.stalls_recycler.adapter = StallsAdapter(this)

        stallsViewModel.stalls.observe(this, Observer {
            (rootview.stalls_recycler.adapter as StallsAdapter).stalls = it
            (rootview.stalls_recycler.adapter as StallsAdapter).notifyDataSetChanged()
        })

        return rootview
    }

    override fun stallSelected(stallId: Int) {
     val bundle = bundleOf("stallId" to stallId)

    }
}