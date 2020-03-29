package com.hakan.naber.data

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.hakan.CreateMessageMutation
import com.hakan.GetAllMessagesQuery
import com.hakan.fragment.MessageFragment
import com.hakan.naber.domain.Resource

abstract class NaberDataSource(protected val apolloClient: ApolloClient){
    abstract suspend fun getMessages() : List<MessageFragment>
    abstract fun createMessage(body: String) : MutableLiveData<Resource<MessageFragment>>

     fun mapRepositoriesResponseToRepositories(response: GetAllMessagesQuery.Data?): List<MessageFragment> {
        return response?.allMessages?.map { it.fragments().messageFragment()} ?: emptyList()
    }

     fun mapCreateMessageToMessage(response: Response<CreateMessageMutation.Data>): MessageFragment {
        return response.data()?.createMessage()?.fragments()?.messageFragment()!!
    }
}