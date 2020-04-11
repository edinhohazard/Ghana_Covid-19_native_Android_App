package com.sam.ui.percountry.ghana

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sam.R
import com.sam.data.mapper.GhanaDailyDataMapper
import com.sam.data.model.ghana.GhanaDaily
import com.sam.data.model.ghana.GhanaPerRegion
import com.sam.data.repository.Repository
import com.sam.ui.adapter.viewholders.TextItem
import com.sam.ui.base.BaseViewItem
import com.sam.ui.base.BaseViewModel
import com.sam.util.Constant
import com.sam.util.SingleLiveEvent
import com.sam.util.ext.addTo
import com.sam.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * fiificodes 22/12/2019.
 */
class CountryGhanaViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _liveItems = MutableLiveData<List<BaseViewItem>>()
    val items: LiveData<List<BaseViewItem>>
        get() = _liveItems

    fun loadData() {
        Observable.zip(
            appRepository.getGhanaDaily(),
            appRepository.getGhanaPerRegion(),
            BiFunction<List<GhanaDaily>, List<GhanaPerRegion>, List<BaseViewItem>> { daily, region ->
                val list = mutableListOf<BaseViewItem>()
                if (region.isNullOrEmpty().not()) {
                    list.add(TextItem(R.string.case_per_region_chart))
                    list.add(GhanaDailyDataMapper.transformIntoCountryRegionGraph(region))
                }
                if (daily.isNullOrEmpty().not()) {
                    list.add(TextItem(R.string.case_daily_chart))
                    list.add(GhanaDailyDataMapper.transformIntoCountryDailyGraph(daily))
                    list.add(TextItem(R.string.case_daily))
                    list.addAll(GhanaDailyDataMapper.transformToPerCountryDaily(daily.reversed()))
                }
                return@BiFunction list
            })
            .doOnSubscribe { _loading.postValue(true) }
            .doFinally {
                _loading.postValue(false)
            }
            .subscribe({
                _liveItems.postValue(it)
            }, {
                it.printStackTrace()
                _toastMessage.postValue(Constant.ERROR_MESSAGE)
            })
            .addTo(compositeDisposable)


    }

}