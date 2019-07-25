package com.dvm.appd.bosm.dbg.di.events

import com.dvm.appd.bosm.dbg.events.viewmodel.EventsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [EventsModule::class])
interface EventsComponent {

    fun injectEvents(factory: EventsViewModelFactory)
}