package com.dvm.appd.bosm.dbg.di.events

import com.dvm.appd.bosm.dbg.events.data.repo.EventsRepository
import com.dvm.appd.bosm.dbg.events.data.room.EventsDao
import com.dvm.appd.bosm.dbg.events.retrofit.EventsService
import com.dvm.appd.bosm.dbg.shared.AppDatabase
import com.google.firebase.firestore.ListenerRegistration
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EventsModule {

    @Provides
    fun provideEventsRepository(eventsDao: EventsDao, eventsService: EventsService): EventsRepository {
        return EventsRepository(eventsDao = eventsDao, eventsService = eventsService)
    }

    @Provides
    fun provideEventsDao(appDatabase: AppDatabase): EventsDao{
        return appDatabase.eventsDao()
    }

    @Provides
    fun provideEventsService(retrofit: Retrofit): EventsService{
        return retrofit.create(EventsService::class.java)
    }
}