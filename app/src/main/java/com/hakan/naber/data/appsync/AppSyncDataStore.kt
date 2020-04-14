package com.hakan.naber.data.appsync

import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.hakan.OnAddMessageSubscription
import com.hakan.naber.data.local.model.Message
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class AppSyncDataStore @Inject constructor(private val appSyncClient: AWSAppSyncClient){

      @ExperimentalCoroutinesApi
      fun onCreateMessage() : Flow<Result<Message>> = callbackFlow {
        val onAddMessageSubscription = OnAddMessageSubscription.builder().build()
        val callback =  object : AppSyncSubscriptionCall.Callback<OnAddMessageSubscription.Data> {
              override fun onFailure(e: ApolloException) {
                  cancel(CancellationException("API ETROR",e))
              }

              override fun onResponse(response: Response<OnAddMessageSubscription.Data?>) {
                  try {
                    offer(Result.success(mapCreateMessageToMessage(response)))
                  } catch (exp: Exception) {
                      Timber.e(exp)
                      if (!isClosedForSend) offer(Result.failure(exp))
                  }
              }

              override fun onCompleted() {
                  Timber.d("egg")
              }
          }
          appSyncClient.subscribe(onAddMessageSubscription).execute(callback)

          awaitClose { callback.onCompleted() }
      }
}