package com.sam.data.source.remote

import com.sam.data.model.BaseResponse
import com.sam.data.model.CovidDaily
import com.sam.data.model.CovidDetail
import com.sam.data.model.CovidOverview
import com.sam.data.model.ghana.GhanaDaily
import com.sam.data.model.ghana.GhanaPerRegion
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

@JvmSuppressWildcards
interface Api {
    @GET("api")
    fun overview(): Observable<CovidOverview>

    @GET("api/daily")
    fun daily(): Observable<List<CovidDaily>>

    @GET("api/confirmed")
    fun confirmed(): Observable<List<CovidDetail>>

    @GET("api/deaths")
    fun deaths(): Observable<List<CovidDetail>>

    @GET("api/recovered")
    fun recovered(): Observable<List<CovidDetail>>

    @GET("api/countries/{country}")
    fun country(@Path("country") country: String): Observable<CovidOverview>



    @GET
    fun getGhanaDaily(@Url url: String = "https://raw.githubusercontent.com/Samuelincoom/botautoupdateGhanapush/master/ghanadaily.json"): Observable<BaseResponse<List<GhanaDaily>>>

    @GET
    fun getGhanaPerRegion(@Url url: String = "https://raw.githubusercontent.com/Samuelincoom/botautoupdateGhanapush/master/ghanaregions.json"): Observable<BaseResponse<List<GhanaPerRegion>>>


}