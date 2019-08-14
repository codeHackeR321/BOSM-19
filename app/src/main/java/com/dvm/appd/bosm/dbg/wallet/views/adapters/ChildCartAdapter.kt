package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartItemsData
import kotlinx.android.synthetic.main.adapter_cart_items_child.view.*

class ChildCartAdapter(private val listener: OnButtonClicked): RecyclerView.Adapter<ChildCartAdapter.ChildCartViewHolder>(){

    var cartItems: List<ModifiedCartItemsData> = emptyList()

    interface OnButtonClicked{

        fun plusButtonClicked(item: ModifiedCartItemsData, quantity: Int)
        fun deleteCartItemClicked(itemId: Int)
    }

    inner class ChildCartViewHolder(view: View): RecyclerView.ViewHolder(view){

        val itemName: TextView = view.itemName
        val quantityPrice: TextView = view.quantityPrice
        val quantity: TextView = view.quantity
        val plus: Button = view.plus
        val minus: Button = view.minus
        val delete: Button = view.delete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildCartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart_items_child, parent, false)
        return ChildCartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ChildCartViewHolder, position: Int) {

        holder.itemName.text = cartItems[position].itemName
        holder.quantityPrice.text = "${cartItems[position].quantity} X ${cartItems[position].price}"
        holder.quantity.text = cartItems[position].quantity.toString()

        holder.plus.setOnClickListener {

            listener.plusButtonClicked(cartItems[position], cartItems[position].quantity + 1)
        }

        holder.minus.setOnClickListener {

            if (cartItems[position].quantity > 1){

                listener.plusButtonClicked(cartItems[position], cartItems[position].quantity - 1)
            }
            else{

                listener.deleteCartItemClicked(cartItems[position].itemId)
            }
        }

        holder.delete.setOnClickListener {

            listener.deleteCartItemClicked(cartItems[position].itemId)
        }
    }
}