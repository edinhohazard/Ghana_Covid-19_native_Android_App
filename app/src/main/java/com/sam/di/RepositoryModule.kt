package com.sam.di

import com.sam.data.repository.AppRepository
import com.sam.data.repository.Repository
import org.koin.dsl.module

/**
 * fiificodes 2020-02-24.
 */
val repositoryModule = module {
    factory<Repository> {
        AppRepository(get(), get(), get())
    }
}