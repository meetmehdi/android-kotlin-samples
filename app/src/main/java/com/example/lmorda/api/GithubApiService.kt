package com.example.lmorda.api

import com.example.lmorda.GITHUB_BASE_URL
import com.example.lmorda.data.RepoRepository.Companion.NETWORK_PAGE_SIZE
import com.example.lmorda.model.GithubRepos
import com.example.lmorda.model.Repo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GithubApiService {

    companion object {
        fun create(): GithubApiService {
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubApiService::class.java)
        }
    }

    @GET("/search/repositories")
    suspend fun getRepos(
        @QueryMap(encoded = true) q: Map<String, String>,
        @Query("page") page: Int? = 1,
        @Query("per_page") itemsPerPage: Int? = NETWORK_PAGE_SIZE
    ): GithubRepos?

    @GET("/search/repositories")
    suspend fun getRepo(
        @Query("id") id: Long
    ): Repo?

}