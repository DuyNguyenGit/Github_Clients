package ir.sdrv.mobilletsample.app.modules

import com.duy.githubclients.presentation.view.detail_page.UserDetailViewModel
import com.duy.githubclients.presentation.view.search_page.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usersListViewModel = module {
    viewModel { SearchViewModel(get()) }
}

val userDetailViewModel = module {
    viewModel { UserDetailViewModel(get()) }
}