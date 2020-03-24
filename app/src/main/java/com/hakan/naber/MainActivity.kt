package com.hakan.naber

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val dataSource: NaberDataSource by lazy {
        (application as App).getDataSource()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataSource.getMessages().observe(this, Observer {
            when (it?.status) {
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
        })


     }



}
