package com.sam.data.model.ghana

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * fiificodes 3/12/2019.
 */
data class GhanaPerRegion(
    @Expose @SerializedName("fid")
    val fid: Int = 0,
    @Expose @SerializedName("deaths")
    val deaths: Int = 0,
    @Expose @SerializedName("confirmed")
    val confirmed: Int = 0,
    @Expose @SerializedName("recovered")
    val recovered: Int = 0,
    @Expose @SerializedName("regioncode")
    val regionCode: Int = 0,
    @Expose @SerializedName("region")
    val regionName: String? = null
)