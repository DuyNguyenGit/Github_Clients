package com.duy.githubclients.domain.repository

import com.duy.githubclients.data.remote.api.base.Resource
import com.duy.githubclients.data.remote.api.models.GetGithubUserResponseModel
import com.duy.githubclients.data.remote.api.models.GithubUserModel

interface GithubApiClient {

    suspend fun getUsersList(query: String, page: Int, pageSize: Int): Resource<GetGithubUserResponseModel>

    suspend fun getUserInfo(username: String): Resource<GithubUserModel>
}