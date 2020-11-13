package com.example.lmorda.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lmorda.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: List<Repo>)

    @Query("SELECT * FROM repos ORDER BY stargazers_count DESC")
    fun getRepos(): DataSource.Factory<Int, Repo>

    @Query("SELECT * FROM repos WHERE id=:id")
    suspend fun getRepo(id: Long): Repo?

}