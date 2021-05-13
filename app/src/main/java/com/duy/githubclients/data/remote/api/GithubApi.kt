package com.duy.githubclients.data.remote.api

import com.duy.githubclients.data.remote.api.models.GetGithubUserResponseModel
import com.duy.githubclients.data.remote.api.models.GithubUserModel
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {

    @GET("search/users?q=duynguyengitin:login")
    suspend fun getUsersList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<GetGithubUserResponseModel>

    @GET("search/users")
    suspend fun getUsersList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<GetGithubUserResponseModel>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<GithubUserModel>
}