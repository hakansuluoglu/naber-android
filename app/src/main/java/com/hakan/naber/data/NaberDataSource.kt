package com.hakan.naber.data

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.hakan.CreateMessageMutation
import com.hakan.GetAllMessagesQuery
import com.hakan.fragment.Message
import com.hakan.naber.domain.Resource

abstract class NaberDataSource(protected val apolloClient: ApolloClient){
    abstract suspend fun getMessages() : List<Message>
    abstract fun createMessage(body: String) : MutableLiveData<Resource<Message>>

     fun mapRepositoriesResponseToRepositories(response: GetAllMessagesQuery.Data?): List<Message> {
        return response?.allMessages?.map { it.fragments().message()} ?: emptyList()
    }

     fun mapCreateMessageToMessage(response: Response<CreateMessageMutation.Data>): Message {
        return response.data()?.createMessage()?.fragments()?.message()!!
    }
}