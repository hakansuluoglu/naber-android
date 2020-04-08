package com.hakan.naber.app.di.module

import androidx.lifecycle.ViewModel
import com.hakan.naber.app.di.ViewModelKey
import com.hakan.naber.ui.features.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
