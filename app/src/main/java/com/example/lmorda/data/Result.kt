package com.example.lmorda.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.lmorda.model.Repo

data class Result(
    val data: LiveData<PagedList<Repo>>,
    val networkErrors: LiveData<String>,
    val loading: LiveData<Boolean>
)
