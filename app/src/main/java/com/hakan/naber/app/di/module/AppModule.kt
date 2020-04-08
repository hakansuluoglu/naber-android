package com.hakan.naber.app.di.module

import android.app.Application
import android.content.Context
import com.hakan.naber.App
import com.hakan.naber.ui.features.main.MainActivity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AppModule {

    var mApplication = App()

    var activity = MainActivity()

    fun AppModule(application: App) {
        mApplication = application
    }

    @Provides
    @Singleton
    fun providesApplication(): Application = mApplication

    @Provides
    @Singleton
    fun providesContext(): Context = mApplication


}