package ir.sdrv.mobilletsample.presentation.datasource

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.duy.githubclients.domain.repository.GithubApiClient
import ir.sdrv.mobilletsample.data.remote.api.models.GithubUserModel

class UsersListDataSourceFactory(private val githubApiClient: GithubApiClient): DataSource.Factory<Int, GithubUserModel>() {

    val liveData: MutableLiveData<UsersListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, GithubUserModel> {
        val usersListDataSource = UsersListDataSource(githubApiClient)
        liveData.postValue(usersListDataSource)
        return usersListDataSource
    }
}