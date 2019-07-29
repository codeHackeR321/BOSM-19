package com.dvm.appd.bosm.dbg.di

import com.dvm.appd.bosm.dbg.di.events.EventsComponent
import com.dvm.appd.bosm.dbg.di.events.EventsModule
import com.dvm.appd.bosm.dbg.di.wallet.WalletComponent
import com.dvm.appd.bosm.dbg.di.wallet.WalletModule
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [AppModule::class])
interface AppComponent {
    fun newWalletComponent(b:WalletModule):WalletComponent

    fun newEventsComponent(a:EventsModule): EventsComponent
}