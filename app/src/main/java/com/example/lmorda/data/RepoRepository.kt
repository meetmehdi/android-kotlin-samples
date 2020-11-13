package com.example.lmorda.data

import androidx.paging.LivePagedListBuilder
import com.example.lmorda.api.RepoApiService
import com.example.lmorda.db.RepoRoomService
import com.example.lmorda.model.Repo
import kotlinx.coroutines.CoroutineScope

class RepoRepository(
    private val repoAPI: RepoApiService,
    private val repoDB: RepoRoomService
) {

    fun getRepos(
        scope: CoroutineScope
    ): Result {
        val boundaryCallback = RepoBoundaryCallback(repoAPI, repoDB, scope)
        return Result(
            LivePagedListBuilder(repoDB.getRepos(), DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build(),
            boundaryCallback.networkErrors,
            boundaryCallback.loading
        )
    }

    suspend fun getRepo(id: Long): Repo? = repoDB.getRepo(id)

    companion object {
        @Volatile
        private var INSTANCE: RepoRepository? = null
        fun getInstance(
            repoApiService: RepoApiService,
            roomService: RepoRoomService
        ): RepoRepository {
            return INSTANCE ?: RepoRepository(
                repoApiService,
                roomService
            ).apply { INSTANCE = this }
        }

        private const val DATABASE_PAGE_SIZE = 20
        const val NETWORK_PAGE_SIZE = 50
    }
}