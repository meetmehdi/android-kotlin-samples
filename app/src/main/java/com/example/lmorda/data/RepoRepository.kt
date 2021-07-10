package com.example.lmorda.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lmorda.R
import com.example.lmorda.STARGAZER_COUNT
import com.example.lmorda.TAG_LOG
import com.example.lmorda.api.RepoApiService
import com.example.lmorda.cache.RepoCache
import com.example.lmorda.model.Repo

class RepoRepository(
    private val repoAPI: RepoApiService,
    private val repoCache: RepoCache
) {

    suspend fun getRepos(
        forceRefresh: Boolean = false,
        sort: String = STARGAZER_COUNT,
        language: String? = null
    ): Result<List<Repo>?> {
        if (forceRefresh || repoCache.repos.isNullOrEmpty()) {
            try {
                repoCache.repos = repoAPI.getRepos(sort, language)
            } catch (ex: Exception) {
                Log.e(TAG_LOG, ex.toString())
                return Result.Error(R.string.repo_error)
            }
        }
        return Result.Success(repoCache.repos)
    }

    fun getRepo(id: Long): Result<Repo> {
        return when (val repo = repoCache.repos?.find { it.id == id }) {
            null -> Result.Error(R.string.repo_error)
            else -> Result.Success(repo)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RepoRepository? = null
        fun getInstance(
            repoApiService: RepoApiService,
            repoCache: RepoCache
        ): RepoRepository {
            return INSTANCE ?: RepoRepository(
                repoApiService,
                repoCache
            ).apply { INSTANCE = this }
        }

        //TODO: Implement Paging
        const val ITEMS_PER_PAGE = 50
    }
}