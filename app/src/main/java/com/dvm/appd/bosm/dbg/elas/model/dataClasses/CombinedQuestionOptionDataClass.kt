package com.dvm.appd.bosm.dbg.elas.model.dataClasses

import java.sql.Timestamp

data class CombinedQuestionOptionDataClass(
    val questionId: Long,
    val optionId: Long,
    val option: String,
    val question: String,
    val category: String
)