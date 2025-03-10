package com.dvm.appd.bosm.dbg.di.notification

import com.dvm.appd.bosm.dbg.notification.NotificationViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [NotificationModule::class])
interface NotificationComponent {

    fun injectNotifications(factory: NotificationViewModelFactory)

}