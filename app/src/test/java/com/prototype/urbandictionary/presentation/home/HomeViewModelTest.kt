package com.prototype.urbandictionary.presentation.home

import androidx.lifecycle.MutableLiveData
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource
import com.prototype.domain.repository.SortingMode
import com.prototype.urbandictionary.presentation.util.ScreenStates
import com.prototype.urbandictionary.util.ViewModelTest
import org.junit.Test

// This is how the ViewModelTest class is used
class HomeViewModelTest : ViewModelTest<HomeViewModel>(){

    private val definitionListMock = listOf(
        DefinitionModel(
            1,
            "definition",
            "permalink",
            1,
            "author",
            "word",
            "writtenOn",
            "example",
            1
        )
    )

    override suspend fun buildViewModel(): HomeViewModel {
        val viewModel = HomeViewModel(mock())

        on(viewModel.fetchWordDefinitionsUseCase.invoke(any(), any()))
            .thenReturn(MutableLiveData(Resource.success(definitionListMock)))

        return viewModel
    }

    // We are gonna check that certain variables are not null
    @Test
    fun varsAreNotNull() = safeTest {
        assertNotNull(fetchWordDefinitionsUseCase)
        assertNotNull(definitionList)
        assertNotNull(isTyping)
        assertNotNull(screenState)
        assertNotNull(sortingMode)
        assertNotNull(searchText)
        assertNotNull(isTypingEvent)
        assertNotNull(definitionsSource)
        assertNotNull(definitionsData)
    }

    @Test
    fun searchWordTest() = safeTest {
        searchText.set("lol")

        searchWord()

        assertEquals(ScreenStates.LOADING, screenState.get())
    }

    @Test
    fun onSearchActionTest() = safeTest {
        onSearchAction()

        assertTrue(searchText.get()?.isEmpty() == true)
    }

    @Test
    fun `User want to change from normal sort into thumbs up sort`() = safeTest {
        sortingMode.set(SortingMode.NORMAL)

        onChangeSortingModeAction()

        assertEquals(SortingMode.THUMBS_UP, sortingMode.get())
    }

    @Test
    fun `User want to change from thumbs up sort into thumbs down sort`() = safeTest {
        sortingMode.set(SortingMode.THUMBS_UP)

        onChangeSortingModeAction()

        assertEquals(SortingMode.THUMBS_DOWN, sortingMode.get())
    }

    @Test
    fun `User want to change from thumbs down sort into normal sort`() = safeTest {
        sortingMode.set(SortingMode.THUMBS_DOWN)

        onChangeSortingModeAction()

        assertEquals(SortingMode.NORMAL, sortingMode.get())
    }

    @Test
    fun onBackActionTest() = safeTest {
        onBackAction()

        assertFalse(isTypingEvent.value == false)
    }

    @Test
    fun onClearActionTest() = safeTest {
        onClearAction()

        assertTrue(searchText.get()?.isEmpty() == true)
    }
}