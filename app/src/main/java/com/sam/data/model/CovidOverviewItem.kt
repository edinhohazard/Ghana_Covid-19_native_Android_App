package com.sam.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * fiificodes 04/12/2019.
 */
data class CovidOverviewItem(
    @Expose @SerializedName("detail") val detail: String? = null,
    @Expose @SerializedName("value") val value: Int = 0
)