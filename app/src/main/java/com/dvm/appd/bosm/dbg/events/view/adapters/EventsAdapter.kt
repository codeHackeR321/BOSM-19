package com.dvm.appd.bosm.dbg.events.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import kotlinx.android.synthetic.main.adapter_events_fragment.view.*

class EventsAdapter(private val listener: OnSportsNameClicked): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>(){

    var sportsName: List<String> = emptyList()

    interface OnSportsNameClicked{
        fun openSportsFragment(name: String)
    }

    inner class EventsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val sportsName: Button = view.sportsEvents
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_events_fragment, parent, false)
        return EventsViewHolder(view)
    }

    override fun getItemCount(): Int = sportsName.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.sportsName.text = sportsName[position]
        holder.sportsName.setOnClickListener {
            listener.openSportsFragment(sportsName[position])
        }
    }

}