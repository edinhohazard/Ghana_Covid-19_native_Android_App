package com.sam.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * fiificodes 22/12/2019.
 */
data class BaseResponse<T>(
    @Expose @SerializedName("data")
    val data: T
)