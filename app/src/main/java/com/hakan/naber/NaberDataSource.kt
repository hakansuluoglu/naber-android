package com.hakan.naber

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.hakan.CreateMessageMutation
import com.hakan.GetAllMessagesQuery
import com.hakan.fragment.MessageFragment

abstract class NaberDataSource(protected val apolloClient: ApolloClient){
    abstract fun getMessages() : MutableLiveData<Resource<List<MessageFragment>>>
    abstract fun createMessage(body: String) : MutableLiveData<Resource<MessageFragment>>

    protected fun mapRepositoriesResponseToRepositories(response: Response<GetAllMessagesQuery.Data>): List<MessageFragment> {
        return response.data()?.allMessages?.map { it.fragments().messageFragment() } ?: emptyList()
    }

    protected fun mapCreateMessageToMessage(response: Response<CreateMessageMutation.Data>): MessageFragment {
        return response.data()?.createMessage()?.fragments()?.messageFragment()!!
    }
}