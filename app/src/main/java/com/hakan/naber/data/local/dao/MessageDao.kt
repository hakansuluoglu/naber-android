package com.hakan.naber.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hakan.naber.data.local.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Message>)

    @Query("DELETE FROM Message")
    suspend fun deleteAll()

    @Query("SELECT * FROM Message")
    fun getMessageList() : Flow<List<Message>>
}