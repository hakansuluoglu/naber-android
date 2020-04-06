package com.hakan.naber.data.network

import android.util.Log
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.hakan.GetAllMessagesQuery
import com.hakan.OnAddMessageSubscription
import com.hakan.fragment.MessageFragment
import com.hakan.naber.data.local.db.NaberDatabase
import com.hakan.naber.data.local.model.Converter
import com.hakan.naber.data.local.model.Message
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.annotation.Nonnull




class NetworkDataSource(private val appSyncClient: AWSAppSyncClient,private val naberDatabase: NaberDatabase){
     fun getMessages() {
         val getAllMessagesQuery = GetAllMessagesQuery.builder().build()
         appSyncClient.query(getAllMessagesQuery).enqueue(getAllCallback)
     }

    private val getAllCallback: GraphQLCall.Callback<GetAllMessagesQuery.Data?> =
        object : GraphQLCall.Callback<GetAllMessagesQuery.Data?>() {

            override fun onResponse(response: Response<GetAllMessagesQuery.Data?>) {
                GlobalScope.launch {
                    Log.d("getAllCallback", response.toString())
                    naberDatabase.messageDao().insertAll(mapRepositoriesResponseToRepositories(response.data()))
                }
            }
            override fun onFailure(@Nonnull e: ApolloException) {
                Log.d("getAllCallback", e.localizedMessage)
            }
        }

      fun subscribeOnCreateMessage() {
         val onAddMessageSubscription = OnAddMessageSubscription.builder().build()
          appSyncClient.subscribe(onAddMessageSubscription).execute(onMessageCreateCallback)

     }

    private val onMessageCreateCallback: AppSyncSubscriptionCall.Callback<OnAddMessageSubscription.Data> =
        object : AppSyncSubscriptionCall.Callback<OnAddMessageSubscription.Data> {
            override fun onFailure(e: ApolloException) {
                Log.d("onMessageCreateCallback", e.localizedMessage)
            }

            override fun onResponse(response: Response<OnAddMessageSubscription.Data?>) {
                GlobalScope.launch {
                    Log.d("onMessageCreateCallback", response.toString())
                    naberDatabase.messageDao().insert(mapCreateMessageToMessage(response))
                }
            }


            override fun onCompleted() {
                Log.d("onMessageCreateCallback", "completed")
            }

        }


     private fun mapRepositoriesResponseToRepositories(response: GetAllMessagesQuery.Data?): List<Message> {
         val listFragment : List<MessageFragment> =  response?.allMessages?.map { it.fragments().messageFragment()} ?: emptyList()
         val listMessage : MutableList<Message> = mutableListOf()
         listFragment.forEach{
             listMessage.add(Message(it.id(),it.body(),Converter.toOffsetDateTime(it.createdAt())))
         }
         return  listMessage
    }

     fun mapCreateMessageToMessage(response: Response<OnAddMessageSubscription.Data?>): Message {
        return Message(response.data()?.addMessage()?.id()!!,
            response.data()?.addMessage()?.body()!!,
            Converter.toOffsetDateTime(response.data()?.addMessage()?.createdAt()!!))
    }
}