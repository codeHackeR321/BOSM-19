package com.dvm.appd.bosm.dbg.Wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R

class StallsAdapter : RecyclerView.Adapter<StallsAdapter.StallsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallsAdapter.StallsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_wallet_stalls, parent, false)
        return StallsViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: StallsAdapter.StallsViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class StallsViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

}