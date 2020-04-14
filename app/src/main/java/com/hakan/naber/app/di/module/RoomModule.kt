package com.hakan.naber.app.di.module

import androidx.room.Room
import com.hakan.naber.App
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.data.local.db.NaberDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
object RoomModule {

    var DATABASE_NAME = "db_naber"

    @Singleton
    @Provides
    fun provideDatabase(context: App): NaberDatabase {
        return Room.databaseBuilder<NaberDatabase>(
            context,
            NaberDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMessageDao(db: NaberDatabase): MessageDao {
        return db.messageDao()
    }

}