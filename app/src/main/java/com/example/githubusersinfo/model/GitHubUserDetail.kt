package com.example.githubusersinfo.model

data class GitHubUserDetail(
    val avatar_url: String,
    val name: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val bio: String,
    val public_repos: Int,
    val public_gists: Int,
    val updated_at: String
)
