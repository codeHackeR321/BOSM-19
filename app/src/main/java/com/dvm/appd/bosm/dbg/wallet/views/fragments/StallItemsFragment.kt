package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.CartData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData
import com.dvm.appd.bosm.dbg.wallet.viewmodel.CartViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.CartViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallItemsViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallItemsViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.StallItemsAdapter
import kotlinx.android.synthetic.main.fra_wallet_stall_items.*
import kotlinx.android.synthetic.main.fra_wallet_stall_items.view.*

class StallItemsFragment : Fragment(), StallItemsAdapter.OnAddClickedListener {

    private lateinit var stallItemsViewModel: StallItemsViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val stallId = arguments?.getInt("stallId")
        val rootView = inflater.inflate(R.layout.fra_wallet_stall_items, container, false)

        stallItemsViewModel = ViewModelProviders.of(this, StallItemsViewModelFactory(1))[StallItemsViewModel::class.java]
        cartViewModel = ViewModelProviders.of(this, CartViewModelFactory())[CartViewModel::class.java]

        rootView.items_recycler.adapter = StallItemsAdapter(this)

        stallItemsViewModel.items.observe(this, Observer {
            (rootView.items_recycler.adapter as StallItemsAdapter).stallItems = it
            (rootView.items_recycler.adapter as StallItemsAdapter).notifyDataSetChanged()
        })

        cartViewModel.modifiedCartItems.observe(this, Observer {
            Log.d("Cart", "Observed: $it")
        })

        return rootView
    }

    override fun addButtonClicked(stallItem: StallItemsData, quantity: Int) {

        cartViewModel.insertCartItems(CartData(stallItem.itemId, quantity, stallItem.stallId))

    }

    override fun deleteCartItemClicked(itemId: Int) {

        cartViewModel.deleteCartItem(itemId)
    }
}