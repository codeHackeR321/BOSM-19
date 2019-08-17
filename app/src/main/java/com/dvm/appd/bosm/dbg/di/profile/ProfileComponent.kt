package com.dvm.appd.bosm.dbg.di.profile

import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModelFactory
import dagger.Subcomponent


@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(factory:ProfileViewModelFactory)
}