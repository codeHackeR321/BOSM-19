package com.dvm.appd.bosm.dbg.elas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.di.elas.ElasModule
import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import javax.inject.Inject

class ElasQuestionViewModelFactory: ViewModelProvider.Factory {

    @Inject
    lateinit var elasRepository: ElasRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newElasComponent(ElasModule()).injectElasQuestion(this)
        return ElasQuestionViewModel(elasRepository) as T
    }
}