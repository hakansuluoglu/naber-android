package com.hakan.naber.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hakan.naber.data.NaberDataSource

class MainViewModelFactory(val naberDataSource: NaberDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(naberDataSource) as T
    }
}
