package com.hakan.naber.domain

import com.hakan.naber.data.appsync.AppSyncDataStore
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.data.local.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class Repository @Inject constructor(
    private val appSyncDataStore: AppSyncDataStore,
    private val messageDao: MessageDao) {

    init {
        GlobalScope.launch {
            subscribeMessage()
        }
    }

    private suspend fun subscribeMessage() {
        appSyncDataStore.onCreateMessage().collect{messageDao.insert(it)}
    }

    fun getMessageList() : Flow<List<Message>> {
        return messageDao.getMessageList()
    }

}