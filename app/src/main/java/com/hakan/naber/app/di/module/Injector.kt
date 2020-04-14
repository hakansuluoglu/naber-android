package com.hakan.naber.app.di.module

import com.hakan.naber.ui.features.main.MainActivity
import com.hakan.naber.ui.signup.SignUpActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSignUpActivity(): SignUpActivity

}