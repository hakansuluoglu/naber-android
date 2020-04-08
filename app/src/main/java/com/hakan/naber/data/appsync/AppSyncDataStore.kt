package com.hakan.naber.data.appsync

import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.hakan.OnAddMessageSubscription
import com.hakan.naber.data.local.model.Message
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AppSyncDataStore @Inject constructor(private val appSyncClient: AWSAppSyncClient){

      @ExperimentalCoroutinesApi
      @InternalCoroutinesApi
      fun onCreateMessage() : Flow<Message> = callbackFlow{
        val onAddMessageSubscription = OnAddMessageSubscription.builder().build()
        val callback =  object : AppSyncSubscriptionCall.Callback<OnAddMessageSubscription.Data> {
              override fun onFailure(e: ApolloException) {
                  cancel(CancellationException("API ETROR",e))
              }

              override fun onResponse(response: Response<OnAddMessageSubscription.Data?>) {
                  sendBlocking(mapCreateMessageToMessage(response))
              }

              override fun onCompleted() {
                  channel.close()
              }
          }
          appSyncClient.subscribe(onAddMessageSubscription).execute(callback)
      }
}