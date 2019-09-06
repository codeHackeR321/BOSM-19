package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedItemsData
import kotlinx.android.synthetic.main.adapter_order_card_items.view.*

class OrderDialogAdapter(): RecyclerView.Adapter<OrderDialogAdapter.ItemsViewHolder>(){

    var items: List<ModifiedItemsData> = emptyList()

    inner class ItemsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val itemName: TextView = view.itemName
        val priceQuantity: TextView = view.priceQuantity
        val totalPrice: TextView = view.totalPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order_card_items, parent, false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {

        holder.itemName.text = items[position].itemName
        holder.priceQuantity.text = "${items[position].quantity} X ${items[position].price}"
        holder.totalPrice.text = "${items[position].price * items[position].quantity}"
    }

}