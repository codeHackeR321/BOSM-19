package com.dvm.appd.bosm.dbg.profile.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.UserShows

class UserTicketsAdapter(): RecyclerView.Adapter<UserTicketsAdapter.UserTicketViewHolder>(){

    var userTickets: List<UserShows> = emptyList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserTicketViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.adapter_user_tickets, p0, false)
        return UserTicketViewHolder(view)
    }

    override fun getItemCount(): Int = userTickets.size

    override fun onBindViewHolder(p0: UserTicketViewHolder, p1: Int) {
        Log.d("sdkljvb","sadkfjvb")
    }

    inner class UserTicketViewHolder(view: View): RecyclerView.ViewHolder(view){

    }
}