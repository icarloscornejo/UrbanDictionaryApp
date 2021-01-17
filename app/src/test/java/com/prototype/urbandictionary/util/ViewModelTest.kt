package com.prototype.urbandictionary.util

import android.os.Build
import androidx.lifecycle.ViewModel
import com.prototype.urbandictionary.test.TestApp
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// This class was created by me on another project and its used on many projects at Applaudo Studios
// It's part of a mini testing package for Activities, Fragments, ViewModels and normal Classes
// The original ones can be seen on:
// Activity: https://pastebin.com/kbzFwTGe
// Fragment: https://pastebin.com/igX2CDfm
// ViewModel: https://pastebin.com/ET89redm
// Injectable: https://pastebin.com/tFJhT87k
// Common: https://pastebin.com/hf2ww3Ee

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.P],
    application = TestApp::class
)
abstract class ViewModelTest<TestViewModel : ViewModel> : InjectableTest() {

    private lateinit var viewModel: TestViewModel

    @Before
    fun setUp() {
        initCommons()

        runBlocking {
            viewModel = buildViewModel()
        }
    }

    abstract suspend fun buildViewModel(): TestViewModel

    open fun transformViewModel(viewModel: TestViewModel): TestViewModel {
        return viewModel
    }

    fun safeTest(action: TestViewModel.() -> Unit) {
        action(transformViewModel(viewModel))
    }
}