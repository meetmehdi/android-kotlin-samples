package com.example.lmorda.utils

import androidx.fragment.app.Fragment
import com.example.lmorda.App

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as App).repoRepository
    return ViewModelFactory(repository, this)
}
