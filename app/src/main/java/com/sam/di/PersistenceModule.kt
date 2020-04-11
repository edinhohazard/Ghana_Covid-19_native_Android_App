package com.sam.di

import com.sam.data.source.generated.AppGeneratedSource
import com.sam.data.source.pref.AppPrefSource
import org.koin.dsl.module

/**
 * fiificodes@live.com 2019-06-14.
 */

val persistenceModule = module {
    single {
        AppPrefSource()
    }

    single {
        AppGeneratedSource()
    }
}


