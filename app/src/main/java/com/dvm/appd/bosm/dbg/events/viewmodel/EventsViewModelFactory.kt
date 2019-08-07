package com.dvm.appd.bosm.dbg.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.di.events.EventsModule
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import javax.inject.Inject

class EventsViewModelFactory: ViewModelProvider.Factory {

    @Inject
    lateinit var eventsRepository: EventsRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newEventsComponent(EventsModule()).injectEvents(this)
        @Suppress("UNCHECKED_CAST")
        return EventsViewModel(eventsRepository) as T
    }
}