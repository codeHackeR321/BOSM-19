package com.dvm.appd.bosm.dbg.events.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.adapter_misc_day.view.*

class MiscDayAdapter(private val listener: OnDaySelected): RecyclerView.Adapter<MiscDayAdapter.MiscDayViewHolder>(){

    var miscDays: List<String> = emptyList()

    interface OnDaySelected{
        fun daySelected(day: String)
    }

    inner class MiscDayViewHolder(view: View): RecyclerView.ViewHolder(view){
        val day: TextView = view.day
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiscDayViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_misc_day, parent, false)

        return MiscDayViewHolder(view)
    }

    override fun getItemCount(): Int = miscDays.size

    override fun onBindViewHolder(holder: MiscDayViewHolder, position: Int) {

        holder.day.text = miscDays[position]
        holder.day.setOnClickListener {
            listener.daySelected(miscDays[position])
        }
    }


}