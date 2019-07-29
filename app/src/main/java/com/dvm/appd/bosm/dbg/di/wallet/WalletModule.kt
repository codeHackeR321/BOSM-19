package com.dvm.appd.bosm.dbg.di.wallet

import com.dvm.appd.bosm.dbg.shared.AppDatabase
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.WalletService
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class WalletModule {

    @Provides
    fun providesWalletRepository(walletService: WalletService, walletDao: WalletDao): WalletRepository {
        return WalletRepository(walletService,walletDao)
    }

    @Provides
    fun providesWalletDao(appDatabase: AppDatabase): WalletDao {
        return appDatabase.walletDao()
    }

    @Provides
    fun providesWalletService(retrofit: Retrofit): WalletService {
        return retrofit.create(WalletService::class.java)
    }
}