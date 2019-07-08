package com.dvm.appd.bosm.dbg.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dvm.appd.bosm.dbg.shared.AppDatabase
import dagger.Module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {

    fun providesApplicaton():Application{
        return application
    }

    fun providesAppDatabase(application: Application):AppDatabase{
        return Room.databaseBuilder(application,AppDatabase::class.java,"bosm.db").build()
    }

    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}