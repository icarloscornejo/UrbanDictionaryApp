package com.prototype.domain.usecase

import androidx.annotation.VisibleForTesting
import com.prototype.domain.repository.SearchRepository
import com.prototype.domain.repository.SortingMode

class FetchWordDefinitionsUseCase(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val searchRepository: SearchRepository
) {
    operator fun invoke(
        word: String?,
        mode: SortingMode = SortingMode.NORMAL
    ) = searchRepository.fetchWordDefinitions(word ?: "", mode)
}