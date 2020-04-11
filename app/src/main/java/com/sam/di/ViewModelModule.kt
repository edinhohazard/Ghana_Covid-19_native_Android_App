package com.sam.di

import com.sam.ui.dailygraph.DailyGraphViewModel
import com.sam.ui.detail.DetailViewModel
import com.sam.ui.overview.DashboardViewModel
import com.sam.ui.percountry.ghana.CountryGhanaViewModel
import com.sam.ui.widget.LocationWidgetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * fiificodes 2020-02-24.
 */

val viewModelModule = module {
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { DailyGraphViewModel(get(), get()) }
    viewModel { CountryGhanaViewModel(get(), get()) }
    viewModel { LocationWidgetViewModel(get(), get()) }
}