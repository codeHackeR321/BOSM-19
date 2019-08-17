package com.dvm.appd.bosm.dbg.profile.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.views.AuthActivity
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModel
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModelFactory
import kotlinx.android.synthetic.main.fra_profile.view.*

class ProfileFragment : Fragment() {

    private val profileViewModel by lazy {
        ViewModelProviders.of(this, ProfileViewModelFactory())[ProfileViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fra_profile, container, false)

        rootView.logout.setOnClickListener {
             profileViewModel.logout()
        }

        profileViewModel.order.observe(this, Observer {
            when(it!!){
                UiState.MoveToLogin -> {
                    activity!!.finishAffinity()
                    startActivity(Intent(context!!,AuthActivity::class.java))
                }
                UiState.ShowIdle ->{
                    rootView.loading.visibility = View.GONE
                }
                UiState.ShowLoading -> {
                    rootView.loading.visibility = View.VISIBLE
                }
            }
        })
        return rootView
    }
}