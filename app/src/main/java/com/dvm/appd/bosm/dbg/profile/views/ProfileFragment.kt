package com.dvm.appd.bosm.dbg.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModel
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModelFactory
import kotlinx.android.synthetic.main.fra_profile.view.*

class ProfileFragment : Fragment() {

    private val profileViewModel by lazy {
        ViewModelProviders.of(this, ProfileViewModelFactory())[ProfileViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fra_profile, container, false)

        profileViewModel.state.observe(this, Observer { state ->
            when (state!!) {
                UiState.MoveToLogin -> {
                    rootView.loading.visibility = View.GONE
                    rootView.findNavController().navigate(R.id.action_profileFragment_to_authFragment)
                }
                UiState.Loading -> {
                    rootView.loading.visibility = View.VISIBLE
                }
                is UiState.ShowProfile -> {
                    rootView.loading.visibility = View.GONE
                    Toast.makeText(context, (state as UiState.ShowProfile).user.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        return rootView
    }
}