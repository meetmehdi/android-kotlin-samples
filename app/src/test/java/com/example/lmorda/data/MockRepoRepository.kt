package com.example.lmorda.data

import com.example.lmorda.model.Owner
import com.example.lmorda.model.Repo
import com.example.lmorda.data.Result

class MockRepoRepository : RepoDataRepository {

    override suspend fun getRepos(
        forceRefresh: Boolean,
        sort: String,
        language: String?
    ): Result<List<Repo>?> {
        return Result.Success(listOf(
            Repo(id = 0L, name = "string", description = "string", html_url = "string",
                stargazers_count = 0, forks = 0, pushed_at = "string", language = "string",
                owner = Owner(login = "string", avatar_url = "string")
            )
        ))
    }

    override suspend fun getRepo(id: Long): Result<Repo> {
        return Result.Success(
            Repo(id = 0L, name = "string", description = "string", html_url = "string",
                stargazers_count = 0, forks = 0, pushed_at = "string", language = "string",
                owner = Owner(login = "string", avatar_url = "string")
            )
        )
    }
}