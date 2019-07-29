package com.dvm.appd.bosm.dbg.events.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData

class MiscEventsAdapter: RecyclerView.Adapter<MiscEventsAdapter.MiscEventsViewHolder>(){

    var miscEvents: List<MiscEventsData> = emptyList()

    inner class MiscEventsViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiscEventsViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = miscEvents.size

    override fun onBindViewHolder(holder: MiscEventsViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}