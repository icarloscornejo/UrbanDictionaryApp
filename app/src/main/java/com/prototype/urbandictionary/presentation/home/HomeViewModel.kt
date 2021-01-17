package com.prototype.urbandictionary.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource
import com.prototype.domain.repository.SortingMode
import com.prototype.domain.usecase.FetchWordDefinitionsUseCase
import com.prototype.urbandictionary.presentation.util.ScreenStates
import com.prototype.urbandictionary.presentation.util.SingleLiveEvent

class HomeViewModel(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fetchWordDefinitionsUseCase: FetchWordDefinitionsUseCase
) : ViewModel() {

    // This is the list of definitions that the RecyclerViewAdapter will have
    val definitionList = mutableListOf<DefinitionModel>()

    // Observable variables that are responsible of certain views
    val isTyping = ObservableBoolean(false)
    val screenState = ObservableField(ScreenStates.DEFAULT)
    val sortingMode = ObservableField(SortingMode.NORMAL)

    // Observable Field for the Search EditText
    val searchText = ObservableField("")

    // I made this an event to handle focus and the keyboard in one function at the fragment
    val isTypingEvent = SingleLiveEvent<Boolean>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var definitionsSource: LiveData<Resource<List<DefinitionModel>>> = MutableLiveData()
    val definitionsData = MediatorLiveData<Resource<List<DefinitionModel>>>()

    // This function can be executed anywhere without launching it in a specific coroutine scope,
    // that is possible thanks to the optimized NetworkBoundResource
    fun searchWord(
        mode: SortingMode = SortingMode.NORMAL
    ) {
        sortingMode.set(mode)
        screenState.set(ScreenStates.LOADING)
        isTypingEvent.postValue(false)

        definitionsData.removeSource(definitionsSource)
        definitionsSource = fetchWordDefinitionsUseCase.invoke(searchText.get(), mode)
        definitionsData.addSource(definitionsSource) { definitionsData.postValue(it) }
    }

    fun onSearchAction() {
        // Resetting the search field to initiate a new word search
        searchText.set("")
        isTypingEvent.postValue(true)
    }

    // This function changes the sorting mode like a 3-way switch
    fun onChangeSortingModeAction() {
        searchWord(
            when (sortingMode.get()) {
                SortingMode.NORMAL -> SortingMode.THUMBS_UP
                SortingMode.THUMBS_UP -> SortingMode.THUMBS_DOWN
                else -> SortingMode.NORMAL
            }
        )
    }

    fun onBackAction() = isTypingEvent.postValue(false)
    fun onClearAction() = searchText.set("")
}