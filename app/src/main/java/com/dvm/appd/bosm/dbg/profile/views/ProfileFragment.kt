package com.dvm.appd.bosm.dbg.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModel
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModelFactory

class ProfileFragment :Fragment() {
    private val profileViewModel by lazy {
        ViewModelProviders.of(this,ProfileViewModelFactory())[ProfileViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fra_profile,container,false)
         

        return rootView
    }
}