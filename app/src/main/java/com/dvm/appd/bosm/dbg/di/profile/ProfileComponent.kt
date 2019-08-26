package com.dvm.appd.bosm.dbg.di.profile


import com.dvm.appd.bosm.dbg.profile.viewmodel.AddMoneyViewModelFactory
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModelFactory
import com.dvm.appd.bosm.dbg.profile.viewmodel.SendMoneyViewModelFactory
import dagger.Subcomponent


@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(factory:ProfileViewModelFactory)
    fun inject(factory:AddMoneyViewModelFactory)
    fun inject(factory: SendMoneyViewModelFactory)
}