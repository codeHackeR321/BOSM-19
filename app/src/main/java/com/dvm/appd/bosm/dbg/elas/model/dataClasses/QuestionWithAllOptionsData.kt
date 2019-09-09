package com.dvm.appd.bosm.dbg.elas.model.dataClasses

import com.dvm.appd.bosm.dbg.elas.model.room.OptionData
import com.dvm.appd.bosm.dbg.elas.model.room.QuestionData

data class QuestionWithAllOptionsData(
    val question: String,
    val category: String,
    val options: List<OptionData>
)