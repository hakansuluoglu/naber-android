package com.hakan.naber.presentation.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hakan.naber.App
import com.hakan.naber.R
import com.hakan.naber.data.local.db.NaberDatabase
import com.hakan.naber.data.local.model.Message
import com.hakan.naber.data.network.NetworkDataSource
import com.hakan.naber.domain.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity() {

    private  var viewModel: MainViewModel? = null
    private val observer = Observer<Resource<List<Message>>> { handleResponse(it) }

    private val networkDataSource: NetworkDataSource by lazy {
        (application as App).getDataSource()
    }

    private val localDataSource: NaberDatabase by lazy {
        NaberDatabase(this)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this,MainViewModelFactory(networkDataSource,localDataSource)).get(MainViewModel::class.java)
        viewModel?.weatherLiveData?.observe(this, observer)
        viewModel!!.getMessageList()

        networkDataSource.subscribeOnCreateMessage()
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
