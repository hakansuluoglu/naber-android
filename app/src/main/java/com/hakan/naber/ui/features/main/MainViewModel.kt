package com.hakan.naber.ui.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakan.naber.data.local.model.Message
import com.hakan.naber.domain.Repository
import com.hakan.naber.domain.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val mutableMessageLiveData = MutableLiveData<Resource<List<Message>>>()
    val messageLiveData = mutableMessageLiveData

     fun getMessageList() = viewModelScope.launch{
        repository.getMessageList()
            .onStart { mutableMessageLiveData.value = Resource.loading(emptyList()) }
            .catch  { mutableMessageLiveData.value = Resource.error (it.localizedMessage,0)}
            .collect {
                mutableMessageLiveData.value = Resource.success(it)
            }
     }

    }
