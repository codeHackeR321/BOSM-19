package com.dvm.appd.bosm.dbg.profile.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedComboData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedShowsTickets
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ShowsTickets
import kotlinx.android.synthetic.main.adapter_tickets.view.*
import java.util.zip.Inflater

class TicketsAdapter(private val listener: TicketsChildAdapter.TicketCartActions): RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>(){

    var comboList: List<ModifiedComboData> = emptyList()
    var showsList: List<ModifiedShowsTickets> = emptyList()
    var names = arrayOf("Combos", "Shows")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TicketsViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.adapter_tickets, p0, false)
        return TicketsViewHolder(view)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(p0: TicketsViewHolder, p1: Int) {

        p0.heading.text = names[p1]
        p0.tickets.adapter = TicketsChildAdapter(listener).apply {
            if (p1 == 0)
                this.comboItems = comboList
            else if (p1 ==1)
                this.showsItems = showsList
        }

        if (p1 == 0){
            (p0.tickets.adapter as TicketsChildAdapter).comboItems = comboList
            (p0.tickets.adapter as TicketsChildAdapter).notifyDataSetChanged()
        }
        else if (p1 ==1){
            (p0.tickets.adapter as TicketsChildAdapter).showsItems = showsList
            (p0.tickets.adapter as TicketsChildAdapter).notifyDataSetChanged()
        }

    }

    inner class TicketsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val heading: TextView = view.heading
        val tickets: RecyclerView = view.ticketsChild
    }

}