package com.hakan.naber.presentation.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hakan.fragment.MessageFragment
import com.hakan.naber.App
import com.hakan.naber.R
import com.hakan.naber.data.NaberDataSource
import com.hakan.naber.domain.Resource

class MainActivity : AppCompatActivity() {

    private  var viewModel: MainViewModel? = null
    private val observer = Observer<Resource<List<MessageFragment>>> { handleResponse(it) }

    private val dataSource: NaberDataSource by lazy {
        (application as App).getDataSource()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this,MainViewModelFactory(dataSource)).get(MainViewModel::class.java)
        viewModel?.weatherLiveData?.observe(this, observer)
        viewModel!!.getMessageList()
     }

    private fun handleResponse(it: Resource<List<MessageFragment>>) {
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
