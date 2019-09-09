package com.dvm.appd.bosm.dbg.di.elas

import com.dvm.appd.bosm.dbg.elas.viewModel.ElasQuestionViewModelFactory
import com.dvm.appd.bosm.dbg.elas.viewModel.ElasViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [ElasModule::class])
interface ElasComponent  {

    fun injectElas(factory: ElasViewModelFactory)
    fun injectElasQuestion(factory: ElasQuestionViewModelFactory)
}