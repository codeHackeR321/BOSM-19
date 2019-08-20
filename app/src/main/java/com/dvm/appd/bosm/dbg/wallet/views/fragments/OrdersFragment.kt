package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrdersViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrdersViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.OrdersAdapter
import kotlinx.android.synthetic.main.fra_wallet_orders.view.*

class OrdersFragment : Fragment(), OrdersAdapter.OnOtpClicked {

    private lateinit var ordersViewModel: OrdersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        ordersViewModel = ViewModelProviders.of(this, OrdersViewModelFactory())[OrdersViewModel::class.java]

        val view = inflater.inflate(R.layout.fra_wallet_orders, container, false)

        view.orderRecycler.adapter = OrdersAdapter(this)

        ordersViewModel.orders.observe(this, Observer {

            (view.orderRecycler.adapter as OrdersAdapter).orderItems = it
            (view.orderRecycler.adapter as OrdersAdapter).notifyDataSetChanged()
        })

        ordersViewModel.progressBarMark.observe(this, Observer {

            if (it == 0){
                view.progressBar.visibility = View.VISIBLE
            }
            else if (it == 1){
                view.progressBar.visibility = View.GONE
            }

        })

        return view
    }

    override fun updateOtpSeen(orderId: Int) {
        (ordersViewModel.progressBarMark as MutableLiveData).postValue(0)
        ordersViewModel.updateOtpSeen(orderId)
    }

}
