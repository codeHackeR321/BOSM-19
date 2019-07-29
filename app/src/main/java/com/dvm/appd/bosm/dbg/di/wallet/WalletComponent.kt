package com.dvm.appd.bosm.dbg.di.wallet

import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallItemsViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.viewmodel.OrdersViewModelFactory
import com.dvm.appd.bosm.dbg.wallet.viewmodel.StallsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [WalletModule::class])
interface WalletComponent {

    fun inject(factory:StallsViewModelFactory)
    fun inject(factory:StallItemsViewModelFactory)
    fun injectOrders(factory: OrdersViewModelFactory)
}