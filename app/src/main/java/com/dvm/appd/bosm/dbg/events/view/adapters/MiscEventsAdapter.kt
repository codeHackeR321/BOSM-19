package com.dvm.appd.bosm.dbg.events.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import kotlinx.android.synthetic.main.adapter_misc_events.view.*

class MiscEventsAdapter(private val listener: OnMarkFavouriteClicked): RecyclerView.Adapter<MiscEventsAdapter.MiscEventsViewHolder>(){

    var miscEvents: List<MiscEventsData> = emptyList()

    interface OnMarkFavouriteClicked{
        fun updateIsFavourite(eventId: String, favouriteMark: Int)
    }

    inner class MiscEventsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val event: TextView = view.eventName
        val description: TextView = view.eventDesc
        val organiser: TextView = view.eventOrg
        val time: TextView = view.eventTime
        val venue: TextView = view.eventVenue
        val markFav: Button = view.markFav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiscEventsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_misc_events, parent, false)
        return MiscEventsViewHolder(view)
    }

    override fun getItemCount(): Int = miscEvents.size

    override fun onBindViewHolder(holder: MiscEventsViewHolder, position: Int) {

        holder.event.text = miscEvents[position].name
        holder.description.text = miscEvents[position].description
        holder.organiser.text = miscEvents[position].organiser
        holder.time.text = miscEvents[position].time
        holder.venue.text = miscEvents[position].venue
        holder.markFav.text = miscEvents[position].isFavourite.toString()

        holder.markFav.setOnClickListener {

            if (miscEvents[position].isFavourite == 1){
                listener.updateIsFavourite(miscEvents[position].id, 0)
            }
            else if (miscEvents[position].isFavourite == 0){
                listener.updateIsFavourite(miscEvents[position].id, 1)
            }
        }
    }

}