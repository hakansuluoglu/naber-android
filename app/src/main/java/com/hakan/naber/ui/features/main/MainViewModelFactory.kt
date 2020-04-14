package com.hakan.naber.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hakan.naber.domain.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModelFactory @Inject constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(
        repository
    ) as T
}