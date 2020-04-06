package com.hakan.naber.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.data.local.model.Converter
import com.hakan.naber.data.local.model.Message

@Database(
    entities = [Message::class], version = 1
)
@TypeConverters(Converter::class)
abstract class NaberDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var instance: NaberDatabase? = null
        var DATABASE_NAME = "db_naber"
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            NaberDatabase::class.java, DATABASE_NAME)
            .build()
    }
}