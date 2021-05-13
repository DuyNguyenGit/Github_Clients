package com.duy.githubclients.di.modules

import com.duy.githubclients.presentation.view.search_page.datasource.UsersListDataSourceFactory
import org.koin.dsl.module

val usersListDataSourceFactory = module {
    single { UsersListDataSourceFactory(get()) }
}