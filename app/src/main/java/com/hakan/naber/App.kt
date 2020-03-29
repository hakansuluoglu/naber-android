package com.hakan.naber

import android.app.Application
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.hakan.naber.data.network.ApolloService
import com.hakan.naber.data.NaberDataSource
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class App : Application() {
    private val baseUrl = "https://b2jkzxmqirbvvlx5g454r34esu.appsync-api.eu-west-1.amazonaws.com/graphql"
    private val apolloClient: ApolloClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", "da2-b2elrhpxsbfvvanxz32chue374")
                    .build()

                chain.proceed(request)
            }
            .build()

        ApolloClient.builder()
            .serverUrl(baseUrl)
            .okHttpClient(okHttpClient)
            .build()

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    fun getDataSource(): NaberDataSource {
        return ApolloService(apolloClient)
    }

    fun get(): Context? {
        return applicationContext
    }
}