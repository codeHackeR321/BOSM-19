package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartData
import kotlinx.android.synthetic.main.adapter_cart_item.view.*

class CartAdapter(private val listener: OnButtonClicked): RecyclerView.Adapter<CartAdapter.ChildCartViewHolder>(){

    var cartItems: List<ModifiedCartData> = emptyList()

    interface OnButtonClicked{

        fun plusButtonClicked(item: ModifiedCartData, quantity: Int)
        fun deleteCartItemClicked(itemId: Int)
    }

    inner class ChildCartViewHolder(view: View): RecyclerView.ViewHolder(view){

        val itemName: TextView = view.itemName
        val quantityPrice: TextView = view.price
        val quantity: TextView = view.quantity
        val plus: Button = view.plus
        val minus: Button = view.minus
        val vendor: TextView = view.vendor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildCartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart_item, parent, false)
        return ChildCartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ChildCartViewHolder, position: Int) {

        holder.itemName.text = cartItems[position].itemName
        holder.quantityPrice.text = "₹ ${cartItems[position].quantity * cartItems[position].price}"
        holder.quantity.text = cartItems[position].quantity.toString()
        holder.vendor.text = cartItems[position].vendorName

        holder.plus.setOnClickListener {

            listener.plusButtonClicked(cartItems[position], cartItems[position].quantity + 1)
        }

        holder.minus.setOnClickListener {

            if (cartItems[position].quantity > 1) {

                listener.plusButtonClicked(cartItems[position], cartItems[position].quantity - 1)
            } else {

                listener.deleteCartItemClicked(cartItems[position].itemId)
            }
        }
    }
}