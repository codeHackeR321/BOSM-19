package com.dvm.appd.bosm.dbg.elas.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class QuestionData(
    @PrimaryKey val questionId: Long,
    val correctAnswerId: Int,
    val timestamp: String,
    val question: String,
    var isAnswered: Boolean,
    val category: String
)


