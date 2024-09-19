package com.example.githubusersinfo.service

import com.example.githubusersinfo.model.GitHubUser
import com.example.githubusersinfo.model.GitHubUserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users")
    fun getUsers(): Call<List<GitHubUser>>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<GitHubUserDetail>
}
