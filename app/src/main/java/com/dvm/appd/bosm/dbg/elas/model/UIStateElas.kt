package com.dvm.appd.bosm.dbg.elas.model

sealed class UIStateElas {
    object Loading: UIStateElas()
    data class Failure(val message: String): UIStateElas()
    data class Questions(val questions: Map<Long, List<CombinedQuestionOptionDataClass>>): UIStateElas()
}