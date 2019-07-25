package com.dvm.appd.bosm.dbg.shared

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvm.appd.bosm.dbg.events.data.room.EventsDao
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.MiscEventsData
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsNamesData
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallData
import com.dvm.appd.bosm.dbg.wallet.data.room.dataclasses.StallItemsData

@Database(entities = [StallData::class,StallItemsData::class, MiscEventsData::class, SportsNamesData::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao
    abstract fun eventsDao(): EventsDao

}