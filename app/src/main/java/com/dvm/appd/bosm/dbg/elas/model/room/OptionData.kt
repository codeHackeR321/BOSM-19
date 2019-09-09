package com.dvm.appd.bosm.dbg.elas.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "option_table")
data class OptionData(
    @PrimaryKey val option_id: Long,
    val option: String,
    val questionId: Long
)