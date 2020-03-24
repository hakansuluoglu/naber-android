package com.hakan.naber.data

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.hakan.GetAllMessagesQuery
import com.hakan.fragment.Message
import com.hakan.naber.presentation.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ApolloService(
    apolloClient: ApolloClient
) :  NaberDataSource(apolloClient){
    private var job: Job? = null

    @ExperimentalCoroutinesApi
    override fun getMessages() : List<Message> {
        val getAllMessagesQuery = GetAllMessagesQuery.builder().build()
        var result : List<Message> = emptyList()
        GlobalScope.launch {
            result =  mapRepositoriesResponseToRepositories(apolloClient.query(getAllMessagesQuery).toDeferred().await().data())
        }
        return result;
    }

    override fun createMessage(body: String): MutableLiveData<Resource<Message>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*
    fun getMessages2() : MutableLiveData<Resource<List<Message>>> {
        val result = MutableLiveData<Resource<List<Message>>>()


        GlobalScope.launch {
            try {
                val response = apolloClient.query(getAllMessagesQuery).toDeferred().await()
                withContext(resultContext) {
                    println()
                    result.value = Resource.success(
                        mapRepositoriesResponseToRepositories(response)
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