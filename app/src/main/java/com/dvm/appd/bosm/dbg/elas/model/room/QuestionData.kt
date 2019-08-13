package com.dvm.appd.bosm.dbg.elas.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "question_table")
data class QuestionData(
    @PrimaryKey val questionId: Long,
    val answerId: Int,
    val timestamp: Timestamp,
    val question: String,
    var isAnswered: Boolean,
    val category: String
)


