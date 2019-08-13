package com.dvm.appd.bosm.dbg.di.elas

import com.dvm.appd.bosm.dbg.elas.model.repo.ElasRepository
import com.dvm.appd.bosm.dbg.elas.model.room.ElasDao
import com.dvm.appd.bosm.dbg.shared.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class ElasModule  {

    @Provides
    fun provideElasRepository(elasDao: ElasDao) : ElasRepository {
        return ElasRepository(elasDao)
    }

    @Provides
    fun provideElasDao(appDatabase: AppDatabase) : ElasDao {
        return appDatabase.elasDao()
    }
}