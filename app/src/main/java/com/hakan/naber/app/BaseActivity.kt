package com.hakan.naber.app

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * All activities should be extended from this activity
 * Created by beyazid on 10/02/2020.
 */
abstract class BaseActivity  : AppCompatActivity(), HasAndroidInjector, LifecycleOwner, CoroutineScope {
    private var mLifecycleRegistry: LifecycleRegistry? = null

    //prevent any kind of exception or crashes
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // this job will be running on the main dispatcher(main thread)

    /**
     * this method will override in activities for getting layout id
     */
    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        job = Job()
        mLifecycleRegistry = LifecycleRegistry(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun getLifecycle(): Lifecycle {
        return if (mLifecycleRegistry != null) {
            mLifecycleRegistry as LifecycleRegistry
        } else {
            mLifecycleRegistry = LifecycleRegistry(this)
            mLifecycleRegistry as LifecycleRegistry
        }
    }

}