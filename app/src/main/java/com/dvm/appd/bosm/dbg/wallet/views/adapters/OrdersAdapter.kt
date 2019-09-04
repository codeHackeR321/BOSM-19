package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData
import kotlinx.android.synthetic.main.adapter_order_items.view.*

class OrdersAdapter(private val listener:OrderCardClick): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>(){

    var orderItems: List<ModifiedOrdersData> = emptyList()

    interface OrderCardClick{
        fun updateOtpSeen(orderId: Int)
        fun showOrderItemDialog(orderId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order_items, parent, false)
        return OrdersViewHolder(view)
    }

    override fun getItemCount(): Int = orderItems.size

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {


        holder.stallName.text = orderItems[position].vendor
        holder.orderId.text = "# ${orderItems[position].orderId}"
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
            4 -> {
                holder.status.setTextColor(Color.rgb(232, 60, 60))
                holder.status.text = "Declined"
            }
        }

        //OTP to be decided
//        if (orderItems[position].otpSeen){
//            holder.otp.text = orderItems[position].otp.toString()
//            holder.otp.isClickable = false
//        }
//        else{
//            holder.otp.setOnClickListener {
//                if (orderItems[position].status == 2){
//                    listener.updateOtpSeen(orderItems[position].orderId)
//                    Log.d("OTP", "Called")
//                }
//                else{
//                    Log.d("OTP", "Status not yet ready ${orderItems[position].status}")
//                }
//            }
//        }

        holder.view.setOnClickListener {
            listener.showOrderItemDialog(orderItems[position].orderId)
        }
    }

    inner class OrdersViewHolder(view: View): RecyclerView.ViewHolder(view){

        val stallName: TextView = view.stallName
        val status: TextView = view.status
        val orderId: TextView = view.orderId
        val price: TextView = view.price
        val view: View = view.view
    }

}