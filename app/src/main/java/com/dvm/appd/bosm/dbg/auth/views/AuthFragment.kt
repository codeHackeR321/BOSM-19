package com.dvm.appd.bosm.dbg.auth.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.viewmodel.AuthViewModel
import com.dvm.appd.bosm.dbg.auth.viewmodel.AuthViewModelFactory
import kotlinx.android.synthetic.main.fra_auth.*
import kotlinx.android.synthetic.main.fra_auth.view.*

class AuthFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fra_auth, container, false)

        authViewModel = ViewModelProviders.of(this, AuthViewModelFactory())[AuthViewModel::class.java]
        rootView.outsteeLogin.setOnClickListener {
          it.findNavController().navigate(R.id.action_authFragment_to_loginOutsteeFragment)
        }

        rootView.bitsianLogin.setOnClickListener {
            authViewModel.login(true)
        }

        return rootView
    }


}
