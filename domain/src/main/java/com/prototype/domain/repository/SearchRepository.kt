package com.prototype.domain.repository

import androidx.lifecycle.LiveData
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource

interface SearchRepository {

    fun fetchWordDefinitions(
        word: String,
        mode: SortingMode
    ): LiveData<Resource<List<DefinitionModel>>>

}