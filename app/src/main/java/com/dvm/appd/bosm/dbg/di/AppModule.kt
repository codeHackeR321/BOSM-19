package com.dvm.appd.bosm.dbg.di

import android.app.Application
import dagger.Module

@Module
class AppModule(private val application: Application) {

    fun providesApplicaton():Application{
        return application
    }


}