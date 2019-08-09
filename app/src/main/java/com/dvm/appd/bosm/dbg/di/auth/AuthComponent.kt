package com.dvm.appd.bosm.dbg.di.auth

import com.dvm.appd.bosm.dbg.auth.viewmodel.AuthViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    fun inject(factory:AuthViewModelFactory)
}