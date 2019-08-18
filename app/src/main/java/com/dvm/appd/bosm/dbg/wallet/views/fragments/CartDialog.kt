package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedCartItemsData
import com.dvm.appd.bosm.dbg.wallet.viewmodel.CartViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.CartViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.views.adapters.CartAdapter
import com.dvm.appd.bosm.dbg.wallet.views.adapters.ChildCartAdapter
import kotlinx.android.synthetic.main.fra_cart_dialog.view.*

class CartDialog: Fragment(), ChildCartAdapter.OnButtonClicked{

    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fra_cart_dialog, container, false)

        cartViewModel = ViewModelProviders.of(this, CartViewModelFactory())[CartViewModel::class.java]

        view.cartRecycler.adapter = CartAdapter(this)

        cartViewModel.cartItems.observe(this, Observer {

            (view.cartRecycler.adapter as CartAdapter).cartItems = it.first
            (view.cartRecycler.adapter as CartAdapter).notifyDataSetChanged()

            view.totalPrice.text = "Rupees ${it.second}"
        })

        view.placeOrder.setOnClickListener {
            cartViewModel.placeOrder()
        }

        return view
    }

    override fun plusButtonClicked(item: ModifiedCartItemsData, quantity: Int) {
        cartViewModel.updateCartItems(item.itemId, quantity)
    }

    override fun deleteCartItemClicked(itemId: Int) {
        cartViewModel.deleteCartItem(itemId)
    }
}