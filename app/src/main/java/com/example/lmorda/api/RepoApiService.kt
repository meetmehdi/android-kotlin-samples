package com.example.lmorda.api

import com.example.lmorda.model.Repo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoApiService internal constructor(
    private val apiService: GithubApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getRepos(
        sort: String,
        language: String?,
        lastPage: Int,
        pageSize: Int
    ): List<Repo>? =
        withContext(ioDispatcher) {
            val params = mutableMapOf<String, String>().apply {
                put(
                    "q",
                    "android" + if (language == null) "+language:java+language:kotlin" else "+language:$language"
                )
            }
            sort.let { params["sort"] = sort }
            apiService.getRepos(params, lastPage, pageSize)?.items
        }

}