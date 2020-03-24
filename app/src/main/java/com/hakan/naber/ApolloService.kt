package com.hakan.naber

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloHttpException
import com.hakan.CreateMessageMutation
import com.hakan.GetAllMessagesQuery
import com.hakan.fragment.MessageFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ApolloService(
    apolloClient: ApolloClient,
    private val processContext: CoroutineContext = Dispatchers.IO,
    private val resultContext: CoroutineContext = Dispatchers.Main
) :  NaberDataSource(apolloClient){
    private var job: Job? = null

    override fun getMessages() : MutableLiveData<Resource<List<MessageFragment>>> {
        val result = MutableLiveData<Resource<List<MessageFragment>>>()
        val getAllMessagesQuery = GetAllMessagesQuery.builder().build()

        GlobalScope.launch {
            try {
                val response = apolloClient.query(getAllMessagesQuery).toDeferred().await()
                withContext(resultContext) {
                    println()
                    result.value = Resource.success(mapRepositoriesResponseToRepositories(response))
                }
            } catch (e: Exception) {
                withContext(resultContext) {
                    if (e is ApolloHttpException)
                        result.value = Resource.error(
                            "${e.message} | code ${e.rawResponse()?.code}",
                            0
                        )
                    else
                        result.value = Resource.error("${e.message}", 0)
                }
            }
        }
        return result;
    }

    override fun createMessage(body: String): MutableLiveData<Resource<MessageFragment>> {
        val result = MutableLiveData<Resource<MessageFragment>>()
        val createMessageMutation = CreateMessageMutation.builder().body(body).build()

        GlobalScope.launch {
            try {
                val response = apolloClient.mutate(createMessageMutation).toDeferred().await()
                withContext(resultContext) {
                    println()
                    result.value = Resource.success(mapCreateMessageToMessage(response))
                }
            } catch (e: Exception) {
                withContext(resultContext) {
                    if (e is ApolloHttpException)
                        result.value = Resource.error(
                            "${e.message} | code ${e.rawResponse()?.code}",
                            0
                        )
                    else
                        result.value = Resource.error("${e.message}", 0)
                }
            }
        }
        return result;
    }
}