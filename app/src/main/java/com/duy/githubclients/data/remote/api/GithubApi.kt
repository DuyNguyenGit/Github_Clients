package ir.sdrv.mobilletsample.data.remote.api

import ir.sdrv.mobilletsample.data.remote.api.models.*
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
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<GetGithubUserResponseModel>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<GithubUserModel>
}