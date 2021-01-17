package com.prototype.data.api.response

import com.google.gson.annotations.SerializedName
import com.prototype.data.db.model.DefinitionEntity
import com.prototype.data.mapper.RoomMapper

data class DefinitionResponse(
    @SerializedName("defid")
    val defid: Int,
    @SerializedName("definition")
    val definition: String,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("thumbs_up")
    val thumbsUp: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("word")
    val word: String,
    @SerializedName("written_on")
    val writtenOn: String,
    @SerializedName("example")
    val example: String,
    @SerializedName("thumbs_down")
    val thumbsDown: Int
) : RoomMapper<DefinitionEntity>() {

    override fun toRoomEntity() = parse<DefinitionEntity>(this)

}