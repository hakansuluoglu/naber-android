package com.hakan.naber

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.hakan.GetMessageQuery
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ApolloService(
    apolloClient: ApolloClient,
    private val processContext: CoroutineContext = Dispatchers.IO,
    private val resultContext: CoroutineContext = Dispatchers.Main
) :  NaberDataSource(apolloClient){
    private var job: Job? = null

    override fun getMessages() {
        val repositoriesQuery = GetMessageQuery.builder().id("hey").build()

        job = CoroutineScope(processContext).launch {
            try {
                val response = apolloClient.query(repositoriesQuery).toDeferred().await()
                withContext(resultContext) {
                    println(response)
                }
            } catch (e: Exception) {
                println(e)
            }
        }

    }
}