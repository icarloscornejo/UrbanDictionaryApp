package com.prototype.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prototype.data.mapper.DomainMapper
import com.prototype.domain.model.DefinitionModel
import org.jetbrains.annotations.NotNull

@Entity
data class DefinitionEntity(
    @PrimaryKey
    @NotNull
    val defid: Int,
    val definition: String,
    val permalink: String,
    val thumbsUp: Int,
    val author: String,
    val word: String,
    val writtenOn: String,
    val example: String,
    val thumbsDown: Int
): DomainMapper<DefinitionModel>() {

    override fun toDomainModel() = parse<DefinitionModel>(this)

}