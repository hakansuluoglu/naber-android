package com.hakan.naber.data.network

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.hakan.GetAllMessagesQuery
import com.hakan.fragment.MessageFragment
import com.hakan.naber.data.NaberDataSource
import com.hakan.naber.domain.Resource

class ApolloService(
    apolloClient: ApolloClient
) :  NaberDataSource(apolloClient){

    override suspend fun getMessages() : List<MessageFragment> {
        val getAllMessagesQuery = GetAllMessagesQuery.builder().build()
        return mapRepositoriesResponseToRepositories(response = apolloClient.query(getAllMessagesQuery).toDeferred().await().data())
    }

    override fun createMessage(body: String): MutableLiveData<Resource<MessageFragment>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*


   */

    /*
    override fun createMessage(body: String): MutableLiveData<Resource<Message>> {
        val result = MutableLiveData<Resource<Message>>()
        val createMessageMutation = CreateMessageMutation.builder().body(body).build()

        GlobalScope.launch {
            try {
                val response = apolloClient.mutate(createMessageMutation).toDeferred().await()
                withContext(resultContext) {
                    println()
                    result.value = Resource.success(
                        mapCreateMessageToMessage(response)
                    )
                }
            } catch (e: Exception) {
                withContext(resultContext) {
                    if (e is ApolloHttpException)
                        result.value = Resource.error(
                            "${e.message} | code ${e.rawResponse()?.code}",
                            0
                        )
                    else
                        result.value =
                            Resource.error("${e.message}", 0)
                }
            }
        }
        return result;
    }
    */
}