package com.example.lmorda

import android.content.Context
import com.example.lmorda.api.GithubApiService
import com.example.lmorda.api.RepoApiService
import com.example.lmorda.data.RepoRepository
import com.example.lmorda.db.RepoDatabase
import com.example.lmorda.db.RepoRoomService

object ServiceLocator {

    @Volatile
    var repoRepository: RepoRepository? = null

    fun provideReposRepository(context: Context): RepoRepository {
        synchronized(this) {
            return repoRepository ?: createRepoRepository(context)
        }
    }

    private fun createRepoRepository(context: Context): RepoRepository {
        val newRepository = RepoRepository.getInstance(
            RepoApiService(GithubApiService.create()),
            RepoRoomService(RepoDatabase.getInstance(context).reposDao())
        )
        repoRepository = newRepository
        return newRepository
    }

}