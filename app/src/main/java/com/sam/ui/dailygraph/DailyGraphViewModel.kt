package com.sam.ui.dailygraph

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sam.data.mapper.CovidDailyDataMapper
import com.sam.data.model.CovidDaily
import com.sam.data.repository.Repository
import com.sam.ui.adapter.viewholders.DailyItem
import com.sam.ui.base.BaseViewModel
import com.sam.util.SingleLiveEvent
import com.sam.util.ext.addTo
import com.sam.util.rx.SchedulerProvider

/**
 * fiificodes 16/12/2019.
 */
class DailyGraphViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _dailyItems = MutableLiveData<List<CovidDaily>>()
    val dailyItems: LiveData<List<CovidDaily>>
        get() = _dailyItems
    val dailyItemsVH: LiveData<List<DailyItem>>
        get() = Transformations.map(_dailyItems) {
            CovidDailyDataMapper.transform(it)
        }

    fun loadCacheDailyData() {
        /*Assume daily data just got fresh data from remote api on previous page
          for UX Purpose, we directly load cache
        */
        _dailyItems.postValue(appRepository.getCacheDaily().orEmpty())
    }

    fun loadRemoteDailyData() {
        appRepository.daily().subscribe({ response ->
            _loading.postValue(false)
            response.data?.let {
                _dailyItems.postValue(it)
            }
        }, {
            _loading.postValue(false)

        }).addTo(compositeDisposable)
    }
}
