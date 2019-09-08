package com.dvm.appd.bosm.dbg.profile.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedComboData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ShowsTickets
import kotlinx.android.synthetic.main.adapter_tickets_child.view.*

class TicketsChildAdapter(private val listener: TicketCartActions): RecyclerView.Adapter<TicketsChildAdapter.TicketsChildViewHolder>(){

    var comboItems: List<ModifiedComboData> = emptyList()
    var showsItems: List<ShowsTickets> = emptyList()

    interface TicketCartActions{

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TicketsChildViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.adapter_tickets_child, p0, false)
        return TicketsChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return when {
            comboItems.isEmpty() -> showsItems.size
            showsItems.isEmpty() -> comboItems.size
            else -> 0
        }
    }

    override fun onBindViewHolder(p0: TicketsChildViewHolder, p1: Int) {

        when{

            comboItems.isEmpty() -> {
                p0.shows.isVisible = false
                p0.name.text = showsItems[p1].name
                p0.price.text = showsItems[p1].price.toString()
            }

            showsItems.isEmpty() -> {
                p0.shows.isVisible = true
                p0.name.text = comboItems[p1].comboName
                p0.price.text = comboItems[p1].price.toString()
                p0.shows.text = comboItems[p1].shows.map { it.showName }.joinToString(" ,")
            }

        }
    }

    inner class TicketsChildViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name: TextView = view.name
        val shows: TextView = view.shows
        val price: TextView = view.price
    }
}