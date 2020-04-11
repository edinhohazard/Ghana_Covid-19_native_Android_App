package com.sam.ui.widget

import com.sam.data.repository.Repository
import com.sam.ui.base.BaseViewModel
import com.sam.util.rx.SchedulerProvider

/**
 * fiificodes 22/12/2019.
 */
data class LocationWidgetViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

}