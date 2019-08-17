package com.dvm.appd.bosm.dbg.elas.model

import java.sql.Timestamp

data class CombinedQuestionOptionDataClass(
    val correctAnswerId: Int,
    val timestamp: String,
    val question: String,
    var isAnswered: Boolean,
    val category: String,
    val id: Long,
    val option: String,
    val questionId: Long,
    val answerId: Int
)