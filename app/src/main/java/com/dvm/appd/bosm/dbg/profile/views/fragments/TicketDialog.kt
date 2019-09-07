package com.dvm.appd.bosm.dbg.profile.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.profile.viewmodel.TicketViewModel
import com.dvm.appd.bosm.dbg.profile.viewmodel.TicketViewModelFactory

class TicketDialog : DialogFragment(){

    private lateinit var ticketsViewModel: TicketViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.dia_tickets, container, false)

        ticketsViewModel = ViewModelProviders.of(this, TicketViewModelFactory())[TicketViewModel::class.java]

        return view
    }
}