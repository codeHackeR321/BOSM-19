package com.dvm.appd.bosm.dbg.events.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.adapter_misc_day.view.*

class MiscDayAdapter(): RecyclerView.Adapter<MiscDayAdapter.MiscDayViewHolder>(){

    inner class MiscDayViewHolder(view: View): RecyclerView.ViewHolder(view){

        val day: Button = view.day
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiscDayViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_misc_day, parent, false)

        return MiscDayViewHolder(view)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: MiscDayViewHolder, position: Int) {

        holder.day.text = "Day"
        holder.day.setOnClickListener {

        }
    }


}