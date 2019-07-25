package com.dvm.appd.bosm.dbg.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import javax.inject.Inject

class MiscEventsModelFactory: ViewModelProvider.Factory {

    @Inject
    lateinit var eventsRepository: EventsRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MiscEventsModel(eventsRepository) as T
    }
}