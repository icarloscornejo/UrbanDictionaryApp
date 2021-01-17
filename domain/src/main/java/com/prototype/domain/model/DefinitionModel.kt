package com.prototype.domain.model

data class DefinitionModel(
    val defid: Int,
    val definition: String,
    val permalink: String,
    val thumbsUp: Int,
    val author: String,
    val word: String,
    val writtenOn: String,
    val example: String,
    val thumbsDown: Int
)