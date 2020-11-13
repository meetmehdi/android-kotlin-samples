package com.example.lmorda

import com.example.lmorda.api.GithubApiService
import com.example.lmorda.api.RepoApiService
import com.example.lmorda.cache.RepoCache
import com.example.lmorda.data.RepoRepository

object ServiceLocator {

    @Volatile
    var repoRepository: RepoRepository? = null

    fun provideReposRepository(): RepoRepository {
        synchronized(this) {
            return repoRepository ?: createRepoRepository()
        }
    }

    private fun createRepoRepository(): RepoRepository {
        val newRepository = RepoRepository.getInstance(
            RepoApiService(GithubApiService.create()),
            RepoCache()
        )
        repoRepository = newRepository
        return newRepository
    }

}