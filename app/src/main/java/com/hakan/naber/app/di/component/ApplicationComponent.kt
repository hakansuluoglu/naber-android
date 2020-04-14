package com.hakan.naber.app.di.component

import com.hakan.naber.App
import com.hakan.naber.app.di.module.ActivityModule
import com.hakan.naber.app.di.module.AppModule
import com.hakan.naber.app.di.module.RoomModule
import com.hakan.naber.app.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
    modules = [
        AppModule::class,
        ActivityModule::class,
        RoomModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)

}