package com.hakan.naber.app.di.module

import com.hakan.naber.ui.features.main.MainActivity
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ActivityModule {

    abstract fun contributeMainActivity(): MainActivity

}