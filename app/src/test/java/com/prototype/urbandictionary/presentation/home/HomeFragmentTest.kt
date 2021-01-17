package com.prototype.urbandictionary.presentation.home

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.MutableLiveData
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource
import com.prototype.urbandictionary.presentation.util.ScreenStates
import com.prototype.urbandictionary.util.FragmentTest
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel

// This is how the FragmentTest class is used
class HomeFragmentTest : FragmentTest<HomeFragment>() {

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

    override fun assignClass() = makeClass<HomeFragment>()

    override suspend fun injectFragment() {
        val viewModel = HomeViewModel(mock())

        on(viewModel.fetchWordDefinitionsUseCase.invoke(any(), any()))
            .thenReturn(MutableLiveData(Resource.success(definitionListMock)))

        injectModule {
            viewModel { viewModel }
        }
    }

    // We are gonna check that certain variables are not null
    @Test
    fun varsAreNotNull() = safeTest {
        assertNotNull(viewModel)
    }

    @Test
    fun `User presses the search button with a valid word`() = safeTest {
        viewModel.searchText.set("word")

        val result = onEditorAction(null, EditorInfo.IME_ACTION_SEARCH, null)

        assertTrue(result)
    }

    @Test
    fun `User presses the search button without a valid word`() = safeTest {
        viewModel.searchText.set("")

        val result = onEditorAction(null, EditorInfo.IME_ACTION_SEARCH, null)

        assertFalse(result)
    }

    @Test
    fun `User proceeds to start typing in the search field`() = safeTest {
        isSearchingEventAction(true)

        assertTrue(viewModel.isTyping.get())
    }

    @Test
    fun `User aborts the typing process in the search field`() = safeTest {
        isSearchingEventAction(false)

        assertFalse(viewModel.isTyping.get())
    }

    @Test
    fun `Loading status at word search`() = safeTest {
        wordDefinitionsDataAction(Resource.loading(null))

        assertEquals(ScreenStates.LOADING, viewModel.screenState.get())
    }

    @Test
    fun `Error status without data at word search`() = safeTest {
        wordDefinitionsDataAction(Resource.error(Throwable("Some error"), null))

        assertEquals(ScreenStates.DEFAULT, viewModel.screenState.get())
    }

    @Test
    fun `Error status with data at word search`() = safeTest {
        wordDefinitionsDataAction(Resource.error(Throwable("Some error"), definitionListMock))

        assertEquals(ScreenStates.HAS_DATA, viewModel.screenState.get())
    }

    @Test
    fun `Success status without data at word search`() = safeTest {
        wordDefinitionsDataAction(Resource.success(null))

        assertEquals(ScreenStates.NO_DATA, viewModel.screenState.get())
    }

    @Test
    fun `Success status with data at word search`() = safeTest {
        wordDefinitionsDataAction(Resource.success(definitionListMock))

        assertEquals(ScreenStates.HAS_DATA, viewModel.screenState.get())
    }

    @Test
    fun populateListTest() = safeTest {
        populateList(definitionListMock)

        assertTrue(viewModel.definitionList.isNotEmpty())
    }

}