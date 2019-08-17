package com.dvm.appd.bosm.dbg.splash.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.views.AuthActivity
import com.dvm.appd.bosm.dbg.splash.viewmodel.SplashViewModel
import com.dvm.appd.bosm.dbg.splash.viewmodel.SplashViewModelFactory

class SplashActivity : AppCompatActivity() {
   private val splashViewModel by lazy {
       ViewModelProviders.of(this,SplashViewModelFactory())[SplashViewModel::class.java]
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.state.observe(this, Observer {
            when(it!!){
                UiState.Login -> {
                    startActivity(Intent(this,AuthActivity::class.java))
                }
                UiState.GoToMainApp -> {
                    startActivity(Intent(this,MainActivity::class.java))
                }
            }
            finish()
        })
    }
}
