package com.sam.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sam.data.mapper.CovidDetailDataMapper
import com.sam.data.model.CovidDetail
import com.sam.data.repository.Repository
import com.sam.ui.adapter.viewholders.LoadingStateItem
import com.sam.ui.base.BaseViewItem
import com.sam.ui.base.BaseViewModel
import com.sam.util.CaseType
import com.sam.util.Constant
import com.sam.util.SingleLiveEvent
import com.sam.util.ext.addTo
import com.sam.util.rx.SchedulerProvider

/**
 * fiificodes 2020-02-24.
 */
class DetailViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private var detailList = listOf<CovidDetail>()
    private var caseType: Int = CaseType.FULL
    private var searchKey: String = ""

    private val _detailListViewItems = MutableLiveData<List<BaseViewItem>>()
    val detailListViewItems: LiveData<List<BaseViewItem>>
        get() = _detailListViewItems

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    val errorMessage = SingleLiveEvent<String>()

    fun findLocation(keyword: String) {
        searchKey = keyword

        val cachePinnedRegion = appRepository.getCachePinnedRegion()

        val transformedList = CovidDetailDataMapper.transform(detailList, caseType)

        val filtered = if (keyword.isNotEmpty()) transformedList.filter {
            (it.regionState?.contains(
                keyword,
                true
            ) ?: false || it.countryRegion.contains(keyword, true))
        }.toMutableList() else transformedList.toMutableList()

        cachePinnedRegion?.let { pin ->
            val position = filtered.indexOfFirst { it.compositeKey() == pin.compositeKey }
            if (position != -1) filtered.set(position, filtered.get(position).copy(isPinned = true))
        }

        _detailListViewItems.postValue(filtered)
    }

    fun getDetail(caseType: Int) {
        this.caseType = caseType
        _detailListViewItems.value = listOf(LoadingStateItem())

        when (caseType) {
            CaseType.RECOVERED -> appRepository.recovered()
            CaseType.DEATHS -> appRepository.deaths()
            CaseType.CONFIRMED -> appRepository.confirmed()
            else -> appRepository.fullStats()
        }.observeOn(schedulerProvider.io())
            .map {
                detailList = it
                val transformedList = CovidDetailDataMapper.transform(it, caseType).toMutableList()

                appRepository.getCachePinnedRegion()?.let { pin ->
                    val position =
                        transformedList.indexOfFirst { it.compositeKey() == pin.compositeKey }
                    if (position != -1) transformedList.set(
                        position,
                        transformedList.get(position).copy(isPinned = true)
                    )
                }

                transformedList.toList()
            }
            .observeOn(schedulerProvider.ui())
            .subscribe({
                _detailListViewItems.postValue(it)
            }, {
                it.printStackTrace()
                errorMessage.postValue(Constant.ERROR_MESSAGE)
            })
            .addTo(compositeDisposable)
    }

    fun removePinnedRegion() {
        appRepository.removePinnedRegion()
            .subscribeOn(schedulerProvider.ui())
            .subscribe({
                findLocation(searchKey) //refresh data
                errorMessage.postValue("Unpinned!")
            }, {
                errorMessage.postValue(it.message)
            })
            .addTo(compositeDisposable)
    }


    fun putPinnedRegion(key: String) {
        detailList.firstOrNull { it.compositeKey == key }?.let {
            appRepository.putPinnedRegion(it)
                .subscribeOn(schedulerProvider.ui())
                .subscribe({
                    findLocation(searchKey) //refresh data
                    errorMessage.postValue("Successfully pinned!")
                }, {
                    errorMessage.postValue(it.message)
                })
                .addTo(compositeDisposable)
        }
    }

}