package com.sam.data.mapper

import com.sam.R
import com.sam.data.model.ghana.GhanaDaily
import com.sam.data.model.ghana.GhanaPerRegion
import com.sam.ui.adapter.viewholders.PerCountryDailyGraphItem
import com.sam.ui.adapter.viewholders.PerCountryDailyItem
import com.sam.ui.adapter.viewholders.PerCountryRegionGraphItem
import com.sam.ui.adapter.viewholders.PerCountryRegionItem

/**
 * fiificodes 22/12/2019.
 */

object GhanaDailyDataMapper {

    fun transformToPerCountryDaily(responses: List<GhanaDaily>?) = responses?.map { response ->
        PerCountryDailyItem(
            response.fid,
            response.newCasePerDay,
            response.totalDeath,
            response.totalRecover,
            response.totalCase,
            response.date,
            response.days,
            R.string.ghana_daily_info
        )
    }.orEmpty()

    fun transformIntoCountryDailyGraph(responses: List<GhanaDaily>?) = PerCountryDailyGraphItem(
        listData = transformToPerCountryDaily(responses.orEmpty())
    )

    fun transformIntoCountryRegion(responses: List<GhanaPerRegion>?) = responses?.map {
        PerCountryRegionItem(
            it.regionCode,
            it.regionName.orEmpty(),
            it.confirmed,
            it.deaths,
            it.recovered
        )
    }.orEmpty()

    fun transformIntoCountryRegionGraph(responses: List<GhanaPerRegion>?) =
        PerCountryRegionGraphItem(
            listData = transformIntoCountryRegion(responses)
        )
}
