package com.dvm.appd.bosm.dbg.di

import com.dvm.appd.bosm.dbg.di.elas.ElasComponent
import com.dvm.appd.bosm.dbg.di.elas.ElasModule
import com.dvm.appd.bosm.dbg.di.auth.AuthComponent
import com.dvm.appd.bosm.dbg.di.auth.AuthModule
import com.dvm.appd.bosm.dbg.di.events.EventsComponent
import com.dvm.appd.bosm.dbg.di.events.EventsModule
import com.dvm.appd.bosm.dbg.di.profile.ProfileComponent
import com.dvm.appd.bosm.dbg.di.profile.ProfileModule
import com.dvm.appd.bosm.dbg.di.wallet.WalletComponent
import com.dvm.appd.bosm.dbg.di.wallet.WalletModule
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [AppModule::class])
interface AppComponent {
    fun newWalletComponent(b:WalletModule):WalletComponent

    fun newEventsComponent(a:EventsModule): EventsComponent

    fun newElasComponent(a: ElasModule): ElasComponent

    fun newAuthComponent(c:AuthModule):AuthComponent

    fun newProfileComponent(d:ProfileModule):ProfileComponent

}