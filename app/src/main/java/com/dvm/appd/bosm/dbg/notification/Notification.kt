package com.dvm.appd.bosm.dbg.notification

import androidx.room.Entity

@Entity(tableName = "notification_table")
data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val channel: String
)