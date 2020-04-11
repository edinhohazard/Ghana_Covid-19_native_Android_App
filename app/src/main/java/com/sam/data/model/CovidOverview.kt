package com.sam.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * fiificodes 04/12/2019.
 */
data class CovidOverview(
    @Expose @SerializedName("confirmed") val confirmed: CovidOverviewItem? = null,
    @Expose @SerializedName("recovered") val recovered: CovidOverviewItem? = null,
    @Expose @SerializedName("deaths") val deaths: CovidOverviewItem? = null
)