package com.dvm.appd.bosm.dbg.auth.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.viewmodel.AuthViewModel
import com.dvm.appd.bosm.dbg.auth.viewmodel.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fra_auth_outstee.*


class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val gso = GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("941586443518-mq055uo5anq6nem2pvjvteidugeo3vgl.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build()
        )
        authViewModel = ViewModelProviders.of(this, AuthViewModelFactory())[AuthViewModel::class.java]

        outsteeLogin.setOnClickListener {
            startActivity(Intent(this, LoginOutsteeActivity::class.java))
        }

        bitsianLogin.setOnClickListener {
            startActivityForResult(gso.signInIntent, 108)
        }

        authViewModel.state.observe(this, Observer {
            when (it!!) {
                LoginState.MoveToMainApp -> {
                    loadingPbr.visibility=View.GONE
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                LoginState.MoveToOnBoarding ->{
                  loadingPbr.visibility = View.GONE
                    startActivity(Intent(this,OnboardingActivity::class.java))
                    finish()
                }
                is LoginState.Failure -> {
                    Toast.makeText(this, (it as LoginState.Failure).message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 108) {
            try {
                val profile = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
                Toast.makeText(this, profile!!.displayName, Toast.LENGTH_SHORT).show()
                authViewModel.login(profile.idToken!!)
            } catch (e: ApiException) {
                Log.d("checke", e.toString())
                Toast.makeText(this, "Sign in Failure!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press Once More to Exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}
