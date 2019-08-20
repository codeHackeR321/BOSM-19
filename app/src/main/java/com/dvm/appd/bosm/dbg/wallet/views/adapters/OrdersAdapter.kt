package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData
import kotlinx.android.synthetic.main.adapter_order_items.view.*

class OrdersAdapter(private val listener:OnOtpClicked): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>(){

    var orderItems: List<ModifiedOrdersData> = emptyList()

    interface OnOtpClicked{
        fun updateOtpSeen(orderId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order_items, parent, false)
        return OrdersViewHolder(view)
    }

    override fun getItemCount(): Int = orderItems.size

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {

        holder.orderNumber.text = "Order ${orderItems[position].orderId}"
        holder.price.text = "₹ ${orderItems[position].totalPrice}"

        when(orderItems[position].status){

            0 -> {
                holder.status.setTextColor(Color.rgb(232, 60, 60))
                holder.status.text = "Pending"
            }
            1 -> {
                holder.status.setTextColor(Color.rgb(253, 200, 87))
                holder.status.text = "Accepted"
            }
            2 -> {
                holder.status.setTextColor(Color.rgb(81 ,168, 81))
                holder.status.text = "Ready"
            }
            3 -> {
                holder.status.setTextColor(Color.rgb(253, 200, 245))
                holder.status.text = "Finished"
            }
        }

        if (orderItems[position].otpSeen){
            holder.otp.text = orderItems[position].otp.toString()
            holder.otp.isClickable = false
        }
        else{
            holder.otp.setOnClickListener {
                if (orderItems[position].status == 2){
                    listener.updateOtpSeen(orderItems[position].orderId)
                    Log.d("OTP", "Called")
                }
                else{
                    Log.d("OTP", "Status not yet ready ${orderItems[position].status}")
                }
            }
        }
    }

    inner class OrdersViewHolder(view: View): RecyclerView.ViewHolder(view){

        val orderNumber: TextView = view.orderNumber
        val otp: TextView = view.otp
        val price: TextView = view.price
        val status: TextView = view.status
    }

}