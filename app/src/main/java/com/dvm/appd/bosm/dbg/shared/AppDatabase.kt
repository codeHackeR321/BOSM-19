package com.dvm.appd.bosm.dbg.shared

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvm.appd.bosm.dbg.elas.model.room.ElasDao
import com.dvm.appd.bosm.dbg.elas.model.room.OptionData
import com.dvm.appd.bosm.dbg.elas.model.room.QuestionData
import com.dvm.appd.bosm.dbg.events.data.room.EventsDao
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.EventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.FavNamesData
import com.dvm.appd.bosm.dbg.notification.Notification
import com.dvm.appd.bosm.dbg.notification.NotificationDao
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.*

@Database(entities = [StallData::class,StallItemsData::class, MiscEventsData::class,
    OrderItemsData::class, OrderData::class, CartData::class, QuestionData::class,
    OptionData::class, SportsData::class, Notification::class, EventsData::class, FavNamesData::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao
    abstract fun eventsDao(): EventsDao
    abstract fun elasDao(): ElasDao
    abstract fun notificationDao(): NotificationDao
}