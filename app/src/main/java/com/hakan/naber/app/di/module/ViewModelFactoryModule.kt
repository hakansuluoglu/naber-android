package com.hakan.naber.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.hakan.naber.ui.features.main.MainViewModelFactory
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindMainViewModelFactory(mainViewModelFactory: MainViewModelFactory): ViewModelProvider.Factory


}
