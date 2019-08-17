package com.dvm.appd.bosm.dbg.elas.model

import com.dvm.appd.bosm.dbg.elas.model.room.OptionData
import com.dvm.appd.bosm.dbg.elas.model.room.QuestionData

data class QuestionWithAllOptionsData(
    val question: QuestionData,
    val options: List<OptionData>
)