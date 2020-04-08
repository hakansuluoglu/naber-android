package com.hakan.naber.data.appsync

import com.apollographql.apollo.api.Response
import com.hakan.GetAllMessagesQuery
import com.hakan.OnAddMessageSubscription
import com.hakan.fragment.MessageFragment
import com.hakan.naber.data.local.model.Converter
import com.hakan.naber.data.local.model.Message

private fun mapRepositoriesResponseToRepositories(response: GetAllMessagesQuery.Data?): List<Message> {
    val listFragment : List<MessageFragment> =  response?.allMessages?.map { it.fragments().messageFragment()} ?: emptyList()
    val listMessage : MutableList<Message> = mutableListOf()
    listFragment.forEach{
        listMessage.add(Message(it.id(),it.body(), Converter.toOffsetDateTime(it.createdAt())))
    }
    return  listMessage
}

fun mapCreateMessageToMessage(response: Response<OnAddMessageSubscription.Data?>): Message {
    return Message(response.data()?.addMessage()?.id()!!,
        response.data()?.addMessage()?.body()!!,
        Converter.toOffsetDateTime(response.data()?.addMessage()?.createdAt()!!))
}