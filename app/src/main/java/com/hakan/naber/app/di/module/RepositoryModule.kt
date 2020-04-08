package com.hakan.naber.app.di.module

import com.hakan.naber.data.appsync.AppSyncDataStore
import com.hakan.naber.data.local.dao.MessageDao
import com.hakan.naber.domain.Repository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module(
    includes = [
        AppSyncModule::class,
        RoomModule::class]
)
class RepositoryModule {

    @Provides
    internal fun provideRepository(appSyncDataStore: AppSyncDataStore,messageDao: MessageDao): Repository = Repository(appSyncDataStore,messageDao)

}