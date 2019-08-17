package com.dvm.appd.bosm.dbg.events.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.card_sports_horizontal.view.*

class GenderDataAdapter: RecyclerView.Adapter<GenderDataAdapter.GenderViewHolder>(){

    var gender: List<String> = emptyList()

    inner class GenderViewHolder(view: View): RecyclerView.ViewHolder(view){

        val editText: EditText = view.editTextGender
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenderViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_sports_horizontal, parent, false)
        return GenderViewHolder(view)
    }

    override fun getItemCount(): Int = gender.size

    override fun onBindViewHolder(holder: GenderViewHolder, position: Int) {
        holder.editText.setText(gender[position])
    }

}