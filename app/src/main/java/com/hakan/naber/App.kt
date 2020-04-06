package com.hakan.naber

import android.app.Application
import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.hakan.naber.data.local.db.NaberDatabase
import com.hakan.naber.data.network.NetworkDataSource
import com.jakewharton.threetenabp.AndroidThreeTen


class App : Application() {
    private val baseUrl = "https://b2jkzxmqirbvvlx5g454r34esu.appsync-api.eu-west-1.amazonaws.com/graphql"
    private val realtimeUrl = "wss://b2jkzxmqirbvvlx5g454r34esu.appsync-realtime-api.eu-west-1.amazonaws.com/graphql"
    private lateinit var awsAppSyncClient: AWSAppSyncClient

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        val awsConfig = AWSConfiguration(this)
        awsAppSyncClient = AWSAppSyncClient.builder()
            .context(this)
            .awsConfiguration(awsConfig)
            .build()

    }

    fun getDataSource(): NetworkDataSource {
        return NetworkDataSource(awsAppSyncClient, NaberDatabase(this))
    }

    fun get(): Context? {
        return applicationContext
    }

    object HeadersProvider {
        val HEADERS = mapOf(
            "x-api-key" to "da2-b2elrhpxsbfvvanxz32chue374"
        )
    }

}
