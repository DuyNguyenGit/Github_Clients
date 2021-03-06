package com.duy.githubclients.presentation.view.detail_page

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duy.githubclients.domain.repository.GithubApiClient
import com.duy.githubclients.data.remote.api.base.Status
import com.duy.githubclients.data.remote.api.models.GithubUserModel
import kotlinx.coroutines.launch

class UserDetailViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val githubUserModel: ObservableField<GithubUserModel> = ObservableField()
    val pageUrl: MutableLiveData<String> = MutableLiveData()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
    }

    fun getUserInfoByUsername(username: String) {
        viewModelScope.launch {
            val result = githubApiClient.getUserInfo(username)
            if (result.status == Status.SUCCESS) {
                githubUserModel.set(result.data)
                errorMessage.set(null)

            } else {
                githubUserModel.set(null)
                errorMessage.set(result.message)
            }

            isWaiting.set(false)
        }
    }

    fun openInBrowser(pageUrl: String?) {
        this.pageUrl.value = pageUrl
    }
}
