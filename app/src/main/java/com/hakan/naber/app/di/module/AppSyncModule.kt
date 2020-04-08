package com.hakan.naber.app.di.module

import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppSyncModule {

    @Provides
    @Singleton
    fun provideAppSync(context: Context): AWSAppSyncClient {
        val awsConfig = AWSConfiguration(context)
        return  AWSAppSyncClient.builder()
            .context(context)
            .awsConfiguration(awsConfig)
            .build()
    }

}