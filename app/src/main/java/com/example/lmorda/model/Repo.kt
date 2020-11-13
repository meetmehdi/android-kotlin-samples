package com.example.lmorda.model

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val html_url: String?,
    val stargazers_count: Int,
    val forks: Int,
    val pushed_at: String,
    val language: String?,
    val owner: Owner
)