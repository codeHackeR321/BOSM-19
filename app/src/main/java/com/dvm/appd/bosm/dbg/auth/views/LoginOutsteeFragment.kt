package com.dvm.appd.bosm.dbg.auth.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.viewmodel.LoginOutsteeViewModel
import com.dvm.appd.bosm.dbg.auth.viewmodel.LoginOutsteeViewModelFactory
import kotlinx.android.synthetic.main.fra_auth_outstee.view.*

class LoginOutsteeFragment : Fragment() {

    private val loginOutsteeViewModel by lazy {
        ViewModelProviders.of(this,LoginOutsteeViewModelFactory())[LoginOutsteeViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fra_auth_outstee, container, false)

        rootView.submitBtn.setOnClickListener {
            when {
                rootView.username.text.toString().isBlank() || rootView.password.text.toString().isBlank() ->
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                else -> {
                     loginOutsteeViewModel.login(rootView.username.text.toString(),rootView.password.text.toString())
                }
            }
        }

        loginOutsteeViewModel.state.observe(this, Observer {
            when(it!!){
                LoginState.Success -> {

                }
                is LoginState.Failure -> {
                   Toast.makeText(context,(it as LoginState.Failure).message,Toast.LENGTH_SHORT).show()
                }
            }
        })

        return rootView
    }
}