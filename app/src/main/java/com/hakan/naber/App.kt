package com.hakan.naber

import android.app.Application
import com.google.firebase.FirebaseApp
import com.hakan.naber.app.di.component.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class App : Application() , HasAndroidInjector  {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        FirebaseApp.initializeApp(this)
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

}
