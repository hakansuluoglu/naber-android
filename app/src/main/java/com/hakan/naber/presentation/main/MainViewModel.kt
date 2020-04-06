package com.hakan.naber.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakan.naber.data.local.db.NaberDatabase
import com.hakan.naber.data.local.model.Message
import com.hakan.naber.data.network.NetworkDataSource
import com.hakan.naber.domain.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel constructor(private val networkDataSource: NetworkDataSource, private val naberDatabase: NaberDatabase) : ViewModel() {

    private val mutableMessageLiveData = MutableLiveData<Resource<List<Message>>>()
    val weatherLiveData = mutableMessageLiveData

    @ExperimentalCoroutinesApi
    fun getMessageList()  {
        viewModelScope.launch {
            naberDatabase.messageDao().getMessageList()
                .onStart { mutableMessageLiveData.value = Resource.loading(emptyList()) }
                .catch  { mutableMessageLiveData.value = Resource.error (it.localizedMessage,0)}
                .collect {
                    if (it.size<14) {
                        networkDataSource.getMessages()
                    } else {
                        mutableMessageLiveData.value = Resource.success(it)
                    }
                }
        }
    }
}