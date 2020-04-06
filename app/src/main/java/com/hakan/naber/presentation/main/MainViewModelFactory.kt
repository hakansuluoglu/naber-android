package com.hakan.naber.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hakan.naber.data.local.db.NaberDatabase
import com.hakan.naber.data.network.NetworkDataSource

class MainViewModelFactory(private val networkDataSource: NetworkDataSource, private val naberDatabase: NaberDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(networkDataSource,naberDatabase) as T
    }
}
