package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.viewmodel.CartViewModel
import com.dvm.appd.bosm.dbg.wallet.viewmodel.CartViewModelFactory
import kotlinx.android.synthetic.main.fra_cart_dialog.*
import kotlinx.android.synthetic.main.fra_cart_dialog.view.*

class CartDialog: DialogFragment(){

    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fra_cart_dialog, container, false)

        cartViewModel = ViewModelProviders.of(this, CartViewModelFactory())[CartViewModel::class.java]

        cartViewModel.modifiedCartItems.observe(this, Observer {

            size.text = it.size.toString()
        })

        view.placeOrder.setOnClickListener {
            cartViewModel.placeOrder()
        }

        return view
    }
}