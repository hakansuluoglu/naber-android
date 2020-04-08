package com.hakan.naber.data.local.db

import androidx.room.Database
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
}