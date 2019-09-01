package com.dvm.appd.bosm.dbg.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.auth.viewmodel.AuthViewModel
import com.dvm.appd.bosm.dbg.di.auth.AuthModule
import com.dvm.appd.bosm.dbg.di.notification.NotificationModule
import javax.inject.Inject

class NotificationViewModelFactory() : ViewModelProvider.Factory {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newNotificationComponent(NotificationModule()).injectNotifications(this)
        return NotificationViewModel(notificationRepository) as T
    }

}