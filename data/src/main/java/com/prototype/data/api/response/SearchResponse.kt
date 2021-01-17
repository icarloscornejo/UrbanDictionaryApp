package com.prototype.data.api.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("list")
    val list: List<DefinitionResponse> = listOf()
)