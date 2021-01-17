package com.prototype.data.impl

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.prototype.data.api.UrbanDictionaryApi
import com.prototype.data.db.dao.DefinitionDao
import com.prototype.data.util.NetworkBoundResource
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource
import com.prototype.domain.repository.SortingMode
import com.prototype.domain.repository.SearchRepository
import java.net.UnknownHostException

class SearchRepositoryImpl(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val urbanDictionaryApi: UrbanDictionaryApi,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val definitionsDao: DefinitionDao
) : SearchRepository {

    override fun fetchWordDefinitions(
        word: String,
        mode: SortingMode
    ): LiveData<Resource<List<DefinitionModel>>> = NetworkBoundResource.create(
        query = {
            when (mode) {
                SortingMode.NORMAL -> definitionsDao.definitionsFromWord(word)
                SortingMode.THUMBS_UP -> definitionsDao.definitionsFromWordThumbsUp(word)
                SortingMode.THUMBS_DOWN -> definitionsDao.definitionsFromWordThumbsDown(word)
            }.map {
                it.toDomainModel()
            }
        },
        fetch = { dbResult ->
            try {
                urbanDictionaryApi.search(word)
            } catch (ex: UnknownHostException) {
                emit(Resource.error(ex, dbResult))
                null
            }
        },
        saveFetchResult = { response ->
            definitionsDao.saveDefinitions(response.list.map { it.toRoomEntity() })
        }
    )

}