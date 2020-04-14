package com.hakan.naber.domain

import com.hakan.naber.data.appsync.AppSyncDataStore
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.data.local.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class Repository @Inject constructor(
    private val appSyncDataStore: AppSyncDataStore,
    private val messageDao: MessageDao) {

    suspend fun subscribeMessage() =  flow {
        appSyncDataStore.onCreateMessage().collect{
            if(it.isSuccess){
                messageDao.insert(it.getOrThrow())
                emit(it.getOrThrow())
            }}
    }

    fun getMessageList() : Flow<List<Message>> {
        return messageDao.getMessageList()
    }

}