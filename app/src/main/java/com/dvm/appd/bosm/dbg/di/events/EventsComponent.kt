package com.dvm.appd.bosm.dbg.di.events

import com.dvm.appd.bosm.dbg.events.viewmodel.EventsViewModelFactory
import com.dvm.appd.bosm.dbg.events.viewmodel.MiscEventsViewModelFactory
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [EventsModule::class])
interface EventsComponent {

    fun injectEvents(factory: EventsViewModelFactory)

    fun injectMiscEvents(factory: MiscEventsViewModelFactory)

    fun injectSportsData(factory: SportsDataViewModelFactory)
}