package com.dvm.appd.bosm.dbg.wallet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dvm.appd.bosm.dbg.R

class StallsFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootview = inflater.inflate(R.layout.fra_wallet_stalls,container,false)

        return rootview
    }
}