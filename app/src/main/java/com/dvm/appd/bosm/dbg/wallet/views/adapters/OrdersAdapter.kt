package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedOrdersData
import kotlinx.android.synthetic.main.adapter_order_items.view.*

class OrdersAdapter: RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>(){

    var orderItems: List<ModifiedOrdersData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order_items, parent, false)
        return OrdersViewHolder(view)
    }

    override fun getItemCount(): Int = orderItems.size

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {

        holder.orderNumber.text = "Order #${orderItems[position].orderId}"
        holder.otp.text = orderItems[position].otp
        holder.price.text = orderItems[position].totalPrice
        holder.status.text = orderItems[position].status
    }

    inner class OrdersViewHolder(view: View): RecyclerView.ViewHolder(view){

        val orderNumber: TextView = view.orderNumber
        val otp: TextView = view.otp
        val price: TextView = view.price
        val status: TextView = view.status
    }

}