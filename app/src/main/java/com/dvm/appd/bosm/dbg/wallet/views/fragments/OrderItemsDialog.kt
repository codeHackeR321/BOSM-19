package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrderItemViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrderItemViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.OrderDialogAdapter
import kotlinx.android.synthetic.main.dia_order_details.*
import kotlinx.android.synthetic.main.dia_order_details.view.*
import java.util.function.LongFunction

class OrderItemsDialog: DialogFragment() {

    private lateinit var orderItemViewModel: OrderItemViewModel

    override fun onStart() {
        super.onStart()
        orderDialog.minHeight = ((parentFragment!!.view!!.height)*0.85).toInt()
        orderDialog.minWidth = ((parentFragment!!.view!!.width)*0.85).toInt()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = arguments?.getInt("orderId")

        val view = inflater.inflate(R.layout.dia_order_details, container, false)

        orderItemViewModel = ViewModelProviders.of(this, OrderItemViewModelFactory(orderId!!))[OrderItemViewModel::class.java]

        view.items.adapter = OrderDialogAdapter()

        orderItemViewModel.order.observe(this, Observer {order ->

            Log.d("OrderDetailsView", order.toString())

            when(order.status){

                0 -> {
                    view.otp.isVisible = false
                    view.status.setTextColor(Color.rgb(232, 60, 60))
                    view.status.text = "Pending"
                }
                1 -> {
                    view.otp.isVisible = false
                    view.status.setTextColor(Color.rgb(253, 200, 87))
                    view.status.text = "Accepted"
                }
                2 -> {
                    view.otp.isVisible = true
                    view.status.setTextColor(Color.rgb(81 ,168, 81))
                    view.status.text = "Ready"
                }
                3 -> {
                    view.otp.isVisible = true
                    view.status.setTextColor(Color.rgb(253, 200, 245))
                    view.status.text = "Finished"
                }
                4 -> {
                    view.otp.isVisible = false
                    view.status.setTextColor(Color.rgb(232, 60, 60))
                    view.status.text = "Declined"
                }
            }

            view.stallName.text = order.vendor
            view.orderId.text = "#${order.orderId}"
            view.price.text = "₹${order.totalPrice}"

            if (order.otpSeen){
                view.otp.text = order.otp.toString()
            }
            else{
                view.otp.setOnClickListener {
                    if (order.status == 2){
                        orderItemViewModel.updateOtpSeen(order.orderId)
                    }
                    else{
                        Log.d("OTP", "Status not yet ready")
                    }
                }
            }

            if (order.status != 3){

                view.items.isVisible = true
                view.textView8.isVisible = true
                view.textView12.isVisible = true
                view.text.isVisible = false
                view.rating1.isVisible = false
                view.rating2.isVisible = false
                view.rating3.isVisible = false
                view.rating4.isVisible = false
                view.rating5.isVisible = false

                (view.items.adapter as OrderDialogAdapter).items = order.items
                (view.items.adapter as OrderDialogAdapter).notifyDataSetChanged()
            }
            else if (order.status == 3){

                view.items.isVisible = false
                view.textView8.isVisible = false
                view.textView12.isVisible = false
                view.text.isVisible = true
                view.rating1.isVisible = true
                view.rating2.isVisible = true
                view.rating3.isVisible = true
                view.rating4.isVisible = true
                view.rating5.isVisible = true

                when (order.rating) {

                    0 -> {
                        view.rating1.setImageResource(R.drawable.ic_star)
                        view.rating2.setImageResource(R.drawable.ic_star)
                        view.rating3.setImageResource(R.drawable.ic_star)
                        view.rating4.setImageResource(R.drawable.ic_star)
                        view.rating5.setImageResource(R.drawable.ic_star)

                        view.rating1.isClickable = true
                        view.rating2.isClickable = true
                        view.rating3.isClickable = true
                        view.rating4.isClickable = true
                        view.rating5.isClickable = true

                    }

                    1 -> {
                        view.rating1.setImageResource(R.drawable.ic_star_full)
                        view.rating2.setImageResource(R.drawable.ic_star)
                        view.rating3.setImageResource(R.drawable.ic_star)
                        view.rating4.setImageResource(R.drawable.ic_star)
                        view.rating5.setImageResource(R.drawable.ic_star)

                        view.rating1.isClickable = false
                        view.rating2.isClickable = false
                        view.rating3.isClickable = false
                        view.rating4.isClickable = false
                        view.rating5.isClickable = false
                    }

                    2 -> {
                        view.rating1.setImageResource(R.drawable.ic_star_full)
                        view.rating2.setImageResource(R.drawable.ic_star_full)
                        view.rating3.setImageResource(R.drawable.ic_star)
                        view.rating4.setImageResource(R.drawable.ic_star)
                        view.rating5.setImageResource(R.drawable.ic_star)

                        view.rating1.isClickable = false
                        view.rating2.isClickable = false
                        view.rating3.isClickable = false
                        view.rating4.isClickable = false
                        view.rating5.isClickable = false
                    }

                    3 -> {
                        view.rating1.setImageResource(R.drawable.ic_star_full)
                        view.rating2.setImageResource(R.drawable.ic_star_full)
                        view.rating3.setImageResource(R.drawable.ic_star_full)
                        view.rating4.setImageResource(R.drawable.ic_star)
                        view.rating5.setImageResource(R.drawable.ic_star)

                        view.rating1.isClickable = false
                        view.rating2.isClickable = false
                        view.rating3.isClickable = false
                        view.rating4.isClickable = false
                        view.rating5.isClickable = false
                    }

                    4 -> {
                        view.rating1.setImageResource(R.drawable.ic_star_full)
                        view.rating2.setImageResource(R.drawable.ic_star_full)
                        view.rating3.setImageResource(R.drawable.ic_star_full)
                        view.rating4.setImageResource(R.drawable.ic_star_full)
                        view.rating5.setImageResource(R.drawable.ic_star)

                        view.rating1.isClickable = false
                        view.rating2.isClickable = false
                        view.rating3.isClickable = false
                        view.rating4.isClickable = false
                        view.rating5.isClickable = false
                    }

                    5 -> {
                        view.rating1.setImageResource(R.drawable.ic_star_full)
                        view.rating2.setImageResource(R.drawable.ic_star_full)
                        view.rating3.setImageResource(R.drawable.ic_star_full)
                        view.rating4.setImageResource(R.drawable.ic_star_full)
                        view.rating5.setImageResource(R.drawable.ic_star_full)

                        view.rating1.isClickable = false
                        view.rating2.isClickable = false
                        view.rating3.isClickable = false
                        view.rating4.isClickable = false
                        view.rating5.isClickable = false
                    }
                }
            }
        })

        view.rating1.setOnClickListener {
            Log.d("Rating","Clicked")
            orderItemViewModel.rateOrder(orderItemViewModel.order.value!!.shell, 1)
        }

        view.rating2.setOnClickListener {
            Log.d("Rating","Clicked")
            orderItemViewModel.rateOrder(orderItemViewModel.order.value!!.shell, 2)
        }

        view.rating3.setOnClickListener {
            Log.d("Rating","Clicked")
            orderItemViewModel.rateOrder(orderItemViewModel.order.value!!.shell, 3)
        }

        view.rating4.setOnClickListener {
            Log.d("Rating","Clicked")
            orderItemViewModel.rateOrder(orderItemViewModel.order.value!!.shell, 4)
        }

        view.rating5.setOnClickListener {
            Log.d("Rating","Clicked")
            orderItemViewModel.rateOrder(orderItemViewModel.order.value!!.shell, 5)
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog

    }
}