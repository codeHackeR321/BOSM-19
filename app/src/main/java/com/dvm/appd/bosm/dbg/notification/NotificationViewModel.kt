package com.dvm.appd.bosm.dbg.notification

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvm.appd.bosm.dbg.shared.util.asMut

class NotificationViewModel(val notificationRepository: NotificationRepository) : ViewModel() {

    var notifications: LiveData<List<Notification>> = MutableLiveData()
    var isLoading: LiveData<Boolean> = MutableLiveData()
    var error: LiveData<String> = MutableLiveData()

    init {
        isLoading.asMut().postValue(true)
        readNotificationsFromRoom()
    }

    @SuppressLint("CheckResult")
    private fun readNotificationsFromRoom() {
        notificationRepository.getNotifications().subscribe({
            if (notifications.value!!.size > 0){
                isLoading.asMut().postValue(false)
            }
            notifications.asMut().postValue(it)
        },{
            // TODO add analytics log
            error.asMut().postValue("Error in local database. Please Restart the app")
        })
    }
}