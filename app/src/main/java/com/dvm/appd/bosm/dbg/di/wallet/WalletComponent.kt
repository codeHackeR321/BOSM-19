package com.dvm.appd.bosm.dbg.di.wallet

import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [WalletModule::class])
interface WalletComponent {

    fun inject(factory:StallsViewModelFactory)
}