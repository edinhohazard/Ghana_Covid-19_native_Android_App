package com.sam.data.mapper

import com.sam.data.model.CovidDaily
import com.sam.data.model.CovidDetail
import com.sam.data.model.CovidOverview
import com.sam.ui.adapter.viewholders.DailyItem
import com.sam.ui.adapter.viewholders.LocationItem
import com.sam.ui.adapter.viewholders.OverviewItem
import com.sam.ui.adapter.viewholders.PinnedItem
import com.sam.util.CaseTypes

object CovidDailyDataMapper {

    fun transform(responses: List<CovidDaily>?) = responses?.map { response ->
        DailyItem(
            response.deltaConfirmed,
            response.deltaRecovered,
            response.mainlandChina,
            response.otherLocations,
            response.reportDate,
            response.incrementRecovered,
            response.incrementConfirmed
        )
    }.orEmpty()
}

object CovidOverviewDataMapper {

    fun transform(response: CovidOverview?) = OverviewItem(
        response?.confirmed?.value ?: 0,
        response?.recovered?.value ?: 0,
        response?.deaths?.value ?: 0)
}

object CovidPinnedDataMapper {

    fun transform(response: CovidDetail?) : PinnedItem? = if(response != null) PinnedItem(
        response.confirmed,
        response.recovered,
        response.deaths,
        response.locationName,
        response.lastUpdate)
    else null
}

object CovidDetailDataMapper {

    fun transform(responses: List<CovidDetail>?, @CaseTypes caseType: Int) = responses?.map { response ->
        LocationItem(
            response.confirmed,
            response.recovered,
            response.deaths,
            response.locationName,
            response.lastUpdate,
            response.lat,
            response.long,
            response.countryRegion,
            response.regionState,
            caseType
        )
    }.orEmpty()
}

object CovidDataMapper{

    fun transformOverviewToUpdatedRegion(covidDetail: CovidDetail, covidOverview: CovidOverview) =  CovidDetail(
        confirmed = covidOverview.confirmed?.value ?: 0,
        deaths = covidOverview.deaths?.value ?: 0,
        recovered = covidOverview.recovered?.value ?: 0,
        countryRegion = covidDetail.countryRegion,
        lastUpdate = covidDetail.lastUpdate,
        lat = covidDetail.lat,
        long = covidDetail.long,
        iso2 = covidDetail.iso2,
        regionState = covidDetail.regionState
    )
}