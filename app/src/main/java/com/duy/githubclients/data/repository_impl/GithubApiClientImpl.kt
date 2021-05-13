package com.duy.githubclients.data.repository_impl

import com.duy.githubclients.domain.repository.GithubApiClient
import com.duy.githubclients.data.remote.api.GithubApi
import com.duy.githubclients.data.remote.api.base.Resource
import com.duy.githubclients.data.remote.api.models.GetGithubUserResponseModel
import com.duy.githubclients.data.remote.api.models.GithubUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubApiClientImpl(private val githubApi: GithubApi) : GithubApiClient {

    override suspend fun getUsersList(
        query: String,
        page: Int,
        pageSize: Int
    ): Resource<GetGithubUserResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = githubApi.getUsersList(query, page, pageSize)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<GetGithubUserResponseModel>("${ex.message}")
        }
    }

    override suspend fun getUserInfo(username: String): Resource<GithubUserModel> =
        withContext(Dispatchers.IO) {
            try {
                val response = githubApi.getUserInfo(username)
                if (response.isSuccessful) {
                    Resource.success(response.body())

                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error<GithubUserModel>("${ex.message}")
            }
        }
}