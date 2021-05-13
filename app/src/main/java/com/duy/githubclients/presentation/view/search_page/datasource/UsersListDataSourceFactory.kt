package com.duy.githubclients.presentation.view.search_page.datasource

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.duy.githubclients.domain.repository.GithubApiClient
import com.duy.githubclients.data.remote.api.models.GithubUserModel

class UsersListDataSourceFactory(private val githubApiClient: GithubApiClient): DataSource.Factory<Int, GithubUserModel>() {
    private var query: String = ""
    val source: MutableLiveData<UsersListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, GithubUserModel> {
        val usersListDataSource = UsersListDataSource(githubApiClient, query)
        source.postValue(usersListDataSource)
        return usersListDataSource
    }

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String){
        this.query = query
        getSource()?.refresh()
    }
}