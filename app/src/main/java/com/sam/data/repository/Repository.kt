package com.sam.data.repository

import com.sam.data.model.CovidDaily
import com.sam.data.model.CovidDetail
import com.sam.data.model.CovidOverview
import com.sam.data.model.ghana.GhanaDaily
import com.sam.data.model.ghana.GhanaPerRegion
import com.sam.ui.adapter.viewholders.PerCountryItem
import io.reactivex.Completable
import io.reactivex.Observable

interface Repository {
    fun overview(): Observable<Result<CovidOverview>>
    fun daily(): Observable<Result<List<CovidDaily>>>
    fun confirmed(): Observable<List<CovidDetail>>
    fun deaths(): Observable<List<CovidDetail>>
    fun recovered(): Observable<List<CovidDetail>>
    fun country(id: String): Observable<CovidOverview>
    fun fullStats(): Observable<List<CovidDetail>>
    fun pinnedRegion(): Observable<Result<CovidDetail>>
    fun putPinnedRegion(data: CovidDetail): Completable
    fun removePinnedRegion(): Completable
    fun getCachePinnedRegion(): CovidDetail?
    fun getCacheOverview(): CovidOverview?
    fun getCacheDaily(): List<CovidDaily>?
    fun getCacheConfirmed(): List<CovidDetail>?
    fun getCacheDeath(): List<CovidDetail>?
    fun getCacheRecovered(): List<CovidDetail>?
    fun getCacheFull(): List<CovidDetail>?
    fun getCacheCountry(id: String): CovidOverview?
    fun getPerCountryItem(): List<PerCountryItem>
    fun getGhanaDaily(): Observable<List<GhanaDaily>>
    fun getGhanaPerRegion(): Observable<List<GhanaPerRegion>>

}