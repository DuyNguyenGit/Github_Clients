package com.duy.githubclients.presentation.view.search_page

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.duy.githubclients.R
import com.duy.githubclients.data.remote.api.base.Status
import com.duy.githubclients.data.remote.api.models.GithubUserModel
import com.duy.githubclients.presentation.view.search_page.datasource.UsersListDataSource
import com.duy.githubclients.presentation.view.search_page.datasource.UsersListDataSourceFactory
import timber.log.Timber
import java.util.concurrent.Executors

class SearchViewModel(private val usersListDataSourceFactory: UsersListDataSourceFactory) :
    ViewModel() {
    private val TAG = SearchViewModel::class.java.simpleName
    lateinit var usersLiveData: LiveData<PagedList<GithubUserModel>>
    var dataSource: MutableLiveData<UsersListDataSource>
    private val _isWaiting: MutableLiveData<Boolean> = MutableLiveData()
    val isWaiting: LiveData<Boolean> = _isWaiting
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    init {
        _isWaiting.value = true
        errorMessage.value = null
        dataSource = usersListDataSourceFactory.source
        initUsersListFactory()
    }

    private fun initUsersListFactory() {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(UsersListDataSource.PAGE_SIZE)
            .setPageSize(UsersListDataSource.PAGE_SIZE)
            .setPrefetchDistance(3)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        usersLiveData =
            LivePagedListBuilder<Int, GithubUserModel>(usersListDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build()
    }

    fun setUpObservers(viewLifecycleOwner: LifecycleOwner) {
        Transformations.switchMap(dataSource) { dataSource -> dataSource.loadStateLiveData }
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    Status.LOADING -> {
                        Timber.d("setUpObservers: >>>LOADING")
                        _isWaiting.value = true
                        errorMessage.value = null
                    }
                    Status.SUCCESS -> {
                        Timber.d("setUpObservers: >>>SUCCESS")
                        _isWaiting.value = false
                        errorMessage.value = null
                    }
                    Status.EMPTY -> {
                        Timber.d("setUpObservers: >>>EMPTY")
                        _isWaiting.value = false
                        errorMessage.value = R.string.msg_users_list_is_empty
                    }
                    else -> {
                        Timber.d("setUpObservers: >>>ELSE")
                        _isWaiting.value = false
                        errorMessage.value = R.string.msg_fetch_users_list_has_error
                    }
                }
            })
    }

    fun search(query: String) {
        val search = query.trim()
        if (usersListDataSourceFactory.getQuery() == search) return
        usersListDataSourceFactory.updateQuery(search)
    }
}