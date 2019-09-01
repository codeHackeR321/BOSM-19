package com.dvm.appd.bosm.dbg.di.notification

import com.dvm.appd.bosm.dbg.notification.NotificationDao
import com.dvm.appd.bosm.dbg.notification.NotificationRepository
import com.dvm.appd.bosm.dbg.shared.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class NotificationModule {

    @Provides
    fun provideNotificationRepository(notificationDao: NotificationDao) : NotificationRepository {
        return NotificationRepository(notificationDao)
    }

    @Provides
    fun provideNotificationDao(appDatabase: AppDatabase) : NotificationDao{
        return appDatabase.notificationDao()
    }

}