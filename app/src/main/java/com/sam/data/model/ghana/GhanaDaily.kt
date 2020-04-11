package com.sam.data.model.ghana

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * fiificodes 3/12/2019.
 */
data class GhanaDaily(
    @Expose @SerializedName("itemnumber")
    val fid: Int = 0,
    @Expose @SerializedName("daynumber")
    val days: Int = 0,
    @Expose @SerializedName("additionaltoday")
    val newCasePerDay: Int = 0,
    @Expose @SerializedName("GhanaTotalconfirmed")
    val totalCase: Int = 0,
    @Expose @SerializedName("GhanaTotaldeath")
    val totalDeath: Int = 0,
    @Expose @SerializedName("GhanaTotalRecovered")
    val totalRecover: Int = 0,
    @Expose @SerializedName("datelong")
    val date: Long = 0
)