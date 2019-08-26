package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrderItemViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrderItemViewModelFactory
import kotlinx.android.synthetic.main.dia_order_details.view.*

class OrderItemsDialog: DialogFragment() {

    private lateinit var orderItemViewModel: OrderItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = arguments?.getInt("orderId")
        val orderNumber = arguments?.getInt("orderNumber")

        val view = inflater.inflate(R.layout.dia_order_details, container, false)

        orderItemViewModel = ViewModelProviders.of(this, OrderItemViewModelFactory(orderId!!))[OrderItemViewModel::class.java]

        orderItemViewModel.order.observe(this, Observer {

            view.orderId.text = "# ${it.orderId}"
            view.otp.text = it.otp.toString()
        })

        view.orderNumber.text = "Order $orderNumber"

        return view
    }
}