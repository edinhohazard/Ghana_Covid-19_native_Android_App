package com.sam.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sam.R
import com.sam.data.mapper.CovidDailyDataMapper
import com.sam.data.mapper.CovidOverviewDataMapper
import com.sam.data.mapper.CovidPinnedDataMapper
import com.sam.data.model.CovidDaily
import com.sam.data.model.CovidDetail
import com.sam.data.model.CovidOverview
import com.sam.data.repository.Repository
import com.sam.data.repository.Result
import com.sam.ui.adapter.viewholders.ErrorStateItem
import com.sam.ui.adapter.viewholders.LoadingStateItem
import com.sam.ui.adapter.viewholders.TextItem
import com.sam.ui.base.BaseViewItem
import com.sam.ui.base.BaseViewModel
import com.sam.util.Constant
import com.sam.util.SingleLiveEvent
import com.sam.util.ext.addTo
import com.sam.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.functions.Function3

/**
 * fiificodes
 */
class DashboardViewModel(
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

    private fun showLoadingState(){
        if(_liveItems.value?.isEmpty() == null ||
                _liveItems.value?.firstOrNull { it is ErrorStateItem } != null){
            _liveItems.value = listOf(LoadingStateItem())
        }
    }

    private fun showErrorState(throwable: Throwable){
        _loading.value = false
        if(_liveItems.value?.isEmpty() == null ||
            _liveItems.value?.firstOrNull { it is ErrorStateItem || it is LoadingStateItem } != null){
            _liveItems.value = listOf(handleThrowable(throwable))
        }
    }

    fun loadDashboard() {
        showLoadingState()

        val overviewObservable = appRepository.overview()
            .observeOn(schedulerProvider.io())

        val dailyObservable = appRepository.daily()
            .observeOn(schedulerProvider.io())

        val pinnedObservable = appRepository.pinnedRegion()
            .observeOn(schedulerProvider.io())

        Observable.combineLatest(
                overviewObservable,
                dailyObservable,
                pinnedObservable,
                Function3<Result<CovidOverview>,
                        Result<List<CovidDaily>>,
                        Result<CovidDetail>,
                        Pair<List<BaseViewItem>, Throwable?>> { overview, daily, pinned ->

                    val items: MutableList<BaseViewItem> = mutableListOf()
                    var currentThrowable: Throwable? = null

                    with(overview){
                        items.add(CovidOverviewDataMapper.transform(data))
                        error?.let { currentThrowable = it }
                    }

                    with(pinned){
                        CovidPinnedDataMapper.transform(data)?.let {
                            items.add(it)
                        }
                        error?.let { currentThrowable = it }
                    }

                    items.add(TextItem(R.string.per_country))
                    items.addAll(appRepository.getPerCountryItem())

                    with(daily){
                        val dailies = CovidDailyDataMapper.transform(data)
                        if(dailies.isNotEmpty()) {
                                items.add(TextItem(R.string.daily_updates, R.string.show_graph))
                                items.addAll(dailies)

                        }
                        error?.let { currentThrowable = it }
                    }

                    return@Function3 items.toList() to currentThrowable
                })
        .observeOn(schedulerProvider.ui())
        .subscribe({ (result, throwable) ->
            _liveItems.postValue(result)


            if(throwable != null) _toastMessage.value = Constant.ERROR_MESSAGE
            _loading.value = false
        }, {
            _toastMessage.value = Constant.ERROR_MESSAGE
            showErrorState(it)
        }).addTo(compositeDisposable)
    }
}