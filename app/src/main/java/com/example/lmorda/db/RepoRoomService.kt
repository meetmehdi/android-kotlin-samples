package com.example.lmorda.db

import androidx.paging.DataSource
import com.example.lmorda.model.Repo

class RepoRoomService(
    private val repoDao: RepoDao
) {
    suspend fun insert(repos: List<Repo>) {
        repoDao.insert(repos)
    }

    fun getRepos(): DataSource.Factory<Int, Repo> {
        return repoDao.getRepos()
    }

    suspend fun getRepo(id: Long): Repo? {
        return repoDao.getRepo(id)
    }
}