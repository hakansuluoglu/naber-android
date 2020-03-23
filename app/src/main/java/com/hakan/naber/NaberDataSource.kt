package com.hakan.naber

import com.apollographql.apollo.ApolloClient

abstract class NaberDataSource(protected val apolloClient: ApolloClient){
    abstract fun getMessages()
}