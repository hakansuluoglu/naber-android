package com.hakan.naber.app.di.module

import android.app.Application
import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.google.firebase.auth.FirebaseAuth
import com.hakan.naber.App
import com.hakan.naber.data.appsync.AppSyncDataStore
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.domain.Repository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
object AppModule {

    var mApplication = App()

    @Provides
    @Singleton
    fun providesApplication(): Application = mApplication

    @Provides
    @Singleton
    fun providesContext(): Context = mApplication

    @Provides
    @Singleton
    fun provideAppSync(context : App): AWSAppSyncClient {
        val awsConfig = AWSConfiguration(context)
        return  AWSAppSyncClient.builder()
            .context(context)
            .awsConfiguration(awsConfig)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRepository(appSyncDataStore: AppSyncDataStore, messageDao: MessageDao): Repository = Repository(appSyncDataStore,messageDao)

    @Provides
    @Singleton
    internal fun provideFirebaseAuth() : FirebaseAuth {
        val auth = FirebaseAuth.getInstance()
        auth.setLanguageCode("tr")
        return auth
    }
}