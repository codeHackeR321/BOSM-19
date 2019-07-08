package com.dvm.appd.bosm.dbg

import android.app.Application
import com.dvm.appd.bosm.dbg.di.AppComponent
import com.dvm.appd.bosm.dbg.di.AppModule
import com.dvm.appd.bosm.dbg.di.DaggerAppComponent

class BOSMApp :Application() {
    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
      appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }
}