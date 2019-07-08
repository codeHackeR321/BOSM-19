package com.dvm.appd.bosm.dbg.wallet.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import kotlinx.android.synthetic.main.adapter_wallet_stalls.view.*

class StallsAdapter (private val listener:OnStallSelectedListener): RecyclerView.Adapter<StallsAdapter.StallsViewHolder>() {

    var stalls: List<StallData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallsAdapter.StallsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_wallet_stalls, parent, false)
        return StallsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stalls.size
    }

    override fun onBindViewHolder(holder: StallsAdapter.StallsViewHolder, position: Int) {
        holder.stallName.text = stalls[position].stallName
        holder.stallImg.setOnClickListener {
            listener.stallSelected(stalls[position].stallId)
        }

    }

    inner class StallsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stallName = view.stallName

        val stallImg = view.stallImage
    }

    interface OnStallSelectedListener{
        fun stallSelected(stallId:Int)
    }

}