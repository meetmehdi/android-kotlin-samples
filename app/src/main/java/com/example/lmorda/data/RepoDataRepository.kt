package com.example.lmorda.data

import com.example.lmorda.R
import com.example.lmorda.STARGAZER_COUNT
import com.example.lmorda.model.Repo

interface RepoDataRepository {

    suspend fun getRepos(
        forceRefresh: Boolean = false,
        sort: String = STARGAZER_COUNT,
        language: String? = null
    ): Result<List<Repo>?>

    suspend fun getRepo(id: Long): Result<Repo>
}