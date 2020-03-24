package com.hakan.naber.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.exception.ApolloHttpException
import com.hakan.fragment.Message
import com.hakan.naber.data.NaberDataSource
import com.hakan.naber.presentation.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class MainViewModel constructor(private val dataSource: NaberDataSource)
    : ViewModel() {

    private val mutableMessageLiveData = MutableLiveData<Resource<List<Message>>>()
    private var getMessageList: Job? = null

    val weatherLiveData = mutableMessageLiveData

    fun getMessageList() = flow {
        emit(dataSource.getMessages())

    }.onStart{
        mutableMessageLiveData.value = Resource.loading(emptyList())
    }.catch{
        when(it){
            is ApolloHttpException ->  mutableMessageLiveData.value = Resource.error(it.localizedMessage,0 )
        }
    }



}