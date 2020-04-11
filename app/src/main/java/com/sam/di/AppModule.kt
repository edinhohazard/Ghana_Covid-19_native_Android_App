package com.sam.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.R
import com.sam.util.rx.AppSchedulerProvider
import com.sam.util.rx.SchedulerProvider
import org.koin.dsl.module
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * fiificodes 2020-02-24.
 */
const val DEFAULT_FONT = "fonts/GoogleSans-Regular.ttf"

val appModule = module {

    single {
        CalligraphyConfig.Builder()
            .setDefaultFontPath(DEFAULT_FONT)
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    factory<SchedulerProvider> {
        AppSchedulerProvider()
    }


    factory {
        LinearLayoutManager(get())
    }

}