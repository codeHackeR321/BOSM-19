package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartData
import kotlinx.android.synthetic.main.adapter_cart_items.view.*

class CartAdapter(private val listener: ChildCartAdapter.OnButtonClicked): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    var cartItems: List<ModifiedCartData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart_items, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.stallName.text = cartItems[position].vendorName
        holder.cartStallItems.adapter = ChildCartAdapter(listener)
        (holder.cartStallItems.adapter as ChildCartAdapter).cartItems = cartItems[position].items
    }

    inner class CartViewHolder(view: View): RecyclerView.ViewHolder(view){

        val stallName: TextView = view.stallName
        val cartStallItems: RecyclerView = view.cartStallItems
    }
}