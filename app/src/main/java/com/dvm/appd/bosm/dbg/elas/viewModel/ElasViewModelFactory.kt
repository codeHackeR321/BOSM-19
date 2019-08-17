package com.dvm.appd.bosm.dbg.elas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.appd.bosm.dbg.BOSMApp
import com.dvm.appd.bosm.dbg.di.elas.ElasModule
import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ElasViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var elasRepository: ElasRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        BOSMApp.appComponent.newElasComponent(ElasModule()).injectElas(this)
        return ElasViewModel(elasRepository) as T
    }
}