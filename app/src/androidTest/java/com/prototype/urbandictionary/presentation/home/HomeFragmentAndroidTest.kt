package com.prototype.urbandictionary.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource
import com.prototype.urbandictionary.R
import com.prototype.urbandictionary.util.FragmentTestAndroid
import io.mockk.every
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel

class HomeFragmentAndroidTest : FragmentTestAndroid<HomeFragment>() {

    private val definitionListMock = listOf(
        DefinitionModel(
            1,
            "word definition",
            "permalink",
            1,
            "author",
            "word",
            "",
            "example",
            1
        )
    )

    override fun assignClass() = makeClass<HomeFragment>()

    override suspend fun injectFragment() {
        val viewModel = HomeViewModel(mock())

        every {
            viewModel.fetchWordDefinitionsUseCase.invoke("word", any())
        } answers {
            MutableLiveData(Resource.success(definitionListMock))
        }

        every {
            viewModel.fetchWordDefinitionsUseCase.invoke("loremipsum", any())
        } answers {
            MutableLiveData(Resource.success(listOf()))
        }

        injectModule {
            viewModel { viewModel }
        }
    }

    @Test
    fun activityDisplaysDefaultStateAtBeginning() = safeTest {
        onView(withId(R.id.tv_blank_state_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchFieldIsShownWhenSearchIconIsClicked() = safeTest {
        onView(withId(R.id.iv_search))
            .perform(click())

        onView(withId(R.id.et_search))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchResultsAreDisplayed() = safeTest {
        onView(withId(R.id.iv_search))
            .perform(click())

        onView(withId(R.id.et_search))
            .perform(typeText("word"))

        onView(withId(R.id.et_search))
            .perform(pressImeActionButton())

        onView(withText("word definition"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun noSearchResults() = safeTest {
        onView(withId(R.id.iv_search))
            .perform(click())

        onView(withId(R.id.et_search))
            .perform(typeText("loremipsum"))

        onView(withId(R.id.et_search))
            .perform(pressImeActionButton())

        onView(withText("Sorry, we couldn't find anything related"))
            .check(matches(isDisplayed()))
    }

}