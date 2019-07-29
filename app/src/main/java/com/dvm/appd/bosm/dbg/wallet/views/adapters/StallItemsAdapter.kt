package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData
import kotlinx.android.synthetic.main.adapter_wallet_stall_items.view.*
import java.util.Collections.emptyList

class StallItemsAdapter(private val listener:OnAddClickedListener) :RecyclerView.Adapter<StallItemsAdapter.ItemsViewHolder>() {

    var stallItems :List<StallItemsData> = emptyList()

    interface OnAddClickedListener{
        fun addButtonClicked(stallId:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_wallet_stall_items,parent,false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stallItems.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.itemName.text = stallItems[position].itemName
        holder.price.text = stallItems[position].price.toString()
        holder.add.setOnClickListener {
            listener.addButtonClicked(stallItems[position].stallId)
        }
    }

    inner class ItemsViewHolder(view: View):RecyclerView.ViewHolder(view){

        var itemName = view.itemName
        var price = view. price
        var add = view.addBtn

    }
}