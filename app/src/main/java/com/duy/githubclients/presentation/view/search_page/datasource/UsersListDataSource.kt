package com.duy.githubclients.presentation.view.search_page.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.duy.githubclients.domain.repository.GithubApiClient
import com.duy.githubclients.data.remote.api.base.Status
import com.duy.githubclients.data.remote.api.models.GithubUserModel
import kotlinx.coroutines.*

class UsersListDataSource(private val githubApiClient: GithubApiClient,
                          private val query: String) :
    PageKeyedDataSource<Int, GithubUserModel>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    val loadStateLiveData: MutableLiveData<Status> = MutableLiveData()
    val totalCount: MutableLiveData<Long> = MutableLiveData()

    companion object {
        const val PAGE_SIZE = 15
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubUserModel>
    ) {
        scope.launch {
            loadStateLiveData.postValue(Status.LOADING)
            totalCount.postValue(0)

            val response = githubApiClient.getUsersList(query, 1, PAGE_SIZE)
            when (response.status) {
                Status.ERROR -> loadStateLiveData.postValue(Status.ERROR)
                Status.EMPTY -> loadStateLiveData.postValue(Status.EMPTY)
                else -> {
                    response.data?.let {
                        callback.onResult(it.items, null, 2)
                        loadStateLiveData.postValue(Status.SUCCESS)
                        totalCount.postValue(it.totalCount)
                    }
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUserModel>) {
        scope.launch {
            val response = githubApiClient.getUsersList(query, params.key, PAGE_SIZE)
            response.data?.let {
                callback.onResult(it.items, params.key + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUserModel>) {

    }

    fun refresh() = this.invalidate()

    override fun invalidate() {
        super.invalidate()
        dataSourceJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }
}