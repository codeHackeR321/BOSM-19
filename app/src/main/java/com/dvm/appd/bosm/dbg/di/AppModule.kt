package com.dvm.appd.bosm.dbg.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dvm.appd.bosm.dbg.shared.AppDatabase
import com.dvm.appd.bosm.dbg.shared.BaseInterceptor
import com.dvm.appd.bosm.dbg.wallet.data.repo.WalletRepository
import com.dvm.appd.bosm.dbg.wallet.data.retrofit.WalletService
import com.dvm.appd.bosm.dbg.wallet.data.room.WalletDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplicaton():Application{
        return application
    }

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application):AppDatabase{
        return Room.databaseBuilder(application,AppDatabase::class.java,"bosm.db")
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://test.bits-bosm.org/")
            .client(OkHttpClient().newBuilder().addInterceptor(BaseInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


}