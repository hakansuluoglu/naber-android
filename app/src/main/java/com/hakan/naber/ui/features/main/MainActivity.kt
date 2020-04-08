package com.hakan.naber.ui.features.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hakan.naber.R
import com.hakan.naber.app.BaseActivity
import com.hakan.naber.data.local.model.Message
import com.hakan.naber.domain.Resource
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : BaseActivity(){

    @Inject
    lateinit  var viewModel: MainViewModel

    @Inject
    lateinit var vmFactoryMain: MainViewModelFactory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private val observer = Observer<Resource<List<Message>>> { handleResponse(it) }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun getLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, vmFactoryMain).get(MainViewModel::class.java)
        viewModel.messageLiveData.observe(this, observer)
        viewModel.getMessageList()
     }

    private fun handleResponse(it: Resource<List<Message>>) {
        when (it.status) {
            Resource.LOADING -> {
                Log.d("MainActivity", "--> Loading...")
            }
            Resource.SUCCESS -> {
                Log.d("MainActivity", "--> Success! | loaded ${it.data?.size ?: 0} records.")
            }
            Resource.ERROR -> {
                Log.d("MainActivity", "--> Error!")
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
