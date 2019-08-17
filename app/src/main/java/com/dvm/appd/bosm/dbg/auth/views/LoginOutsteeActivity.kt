package com.dvm.appd.bosm.dbg.auth.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.viewmodel.LoginOutsteeViewModel
import com.dvm.appd.bosm.dbg.auth.viewmodel.LoginOutsteeViewModelFactory
import kotlinx.android.synthetic.main.fra_auth_outstee.*
import kotlinx.android.synthetic.main.fra_auth_outstee.view.*
import kotlinx.android.synthetic.main.fra_auth_outstee.view.loading

class LoginOutsteeActivity : AppCompatActivity(){

    private val loginOutsteeViewModel by lazy {
        ViewModelProviders.of(this,LoginOutsteeViewModelFactory())[LoginOutsteeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fra_auth_outstee)
        submitBtn.setOnClickListener {
            when {
                username.text.toString().isBlank() || password.text.toString().isBlank() ->
                    Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                else -> {
                   loading.visibility = View.VISIBLE
                    loginOutsteeViewModel.login(username.text.toString(),password.text.toString())
                }
            }
        }

        loginOutsteeViewModel.state.observe(this, Observer {
            when(it!!){
                LoginState.Success -> {
                    loading.visibility = View.GONE
                    startActivity(Intent(this,MainActivity::class.java))
                }
                is LoginState.Failure -> {
                    loading.visibility = View.GONE
                    Toast.makeText(this,(it as LoginState.Failure).message,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}