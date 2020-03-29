package com.hakan.naber.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.data.local.model.Message

@Database(
    entities = [Message::class], version = 1
)
abstract class NaberDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}