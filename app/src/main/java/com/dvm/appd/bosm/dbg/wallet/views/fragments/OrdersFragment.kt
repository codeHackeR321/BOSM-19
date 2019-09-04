package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedItemsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrdersViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrdersViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.OrdersAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_wallet_orders.view.*

class OrdersFragment : Fragment(), OrdersAdapter.OrderCardClick {

    private lateinit var ordersViewModel: OrdersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        ordersViewModel = ViewModelProviders.of(this, OrdersViewModelFactory())[OrdersViewModel::class.java]

        val view = inflater.inflate(R.layout.fra_wallet_orders, container, false)

        activity!!.mainView.setBackgroundResource(R.drawable.orders_title)
        activity!!.fragmentName.text = "Orders"

        activity!!.cart.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_order_history_to_action_cart)
        }

        activity!!.profile.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_order_history_to_action_profile)
        }

        activity!!.notifications.setOnClickListener {
            this.findNavController().navigate(R.id.action_action_order_history_to_notificationFragment)
        }
        view.orderRecycler.adapter = OrdersAdapter(this)

        ordersViewModel.orders.observe(this, Observer {

            (view.orderRecycler.adapter as OrdersAdapter).orderItems = it
            (view.orderRecycler.adapter as OrdersAdapter).notifyDataSetChanged()
        })

        ordersViewModel.progressBarMark.observe(this, Observer {

            if (it == 0){
                view.progressBar.visibility = View.VISIBLE
                activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }
            else if (it == 1){
                view.progressBar.visibility = View.GONE
                activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })

        ordersViewModel.error.observe(this, Observer {
            if (it != null){
                Toast.makeText(context, ordersViewModel.error.value, Toast.LENGTH_LONG).show()
                (ordersViewModel.error as MutableLiveData).postValue(null)
            }
        })

        return view
    }

    override fun updateOtpSeen(orderId: Int) {
        (ordersViewModel.progressBarMark as MutableLiveData).postValue(0)
        ordersViewModel.updateOtpSeen(orderId)
    }

    override fun showOrderItemDialog(orderId: Int, orderNumber: Int) {
        val bundle = bundleOf("orderId" to orderId, "orderNumber" to orderNumber)
        OrderItemsDialog().apply { arguments = bundle }.show(childFragmentManager, "OrderItemDialog")
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
