package com.dvm.appd.bosm.dbg.profile.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.ModifiedTicketsData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.TicketsData
import kotlinx.android.synthetic.main.adapter_tickets.view.*

class TicketsAdapter(private val listener: TicketsChildAdapter.TicketCartActions): RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>(){

    var ticketsList: List<ModifiedTicketsData> = emptyList()
    var names = arrayOf("combo", "show")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TicketsViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.adapter_tickets, p0, false)
        return TicketsViewHolder(view)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(p0: TicketsViewHolder, p1: Int) {

        p0.heading.text = names[p1].capitalize()
        p0.tickets.adapter = TicketsChildAdapter(listener).apply {
                this.tickets = ticketsList.filter { it.type == names[p1] }
            this.notifyDataSetChanged()
        }
        (p0.tickets.adapter as TicketsChildAdapter).tickets = ticketsList.filter { it.type == names[p1] }
        (p0.tickets.adapter as TicketsChildAdapter).notifyDataSetChanged()


    }

    inner class TicketsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val heading: TextView = view.heading
        val tickets: RecyclerView = view.ticketsChild
    }

}