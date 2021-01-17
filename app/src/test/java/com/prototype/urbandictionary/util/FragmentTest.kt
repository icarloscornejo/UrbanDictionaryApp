package com.prototype.urbandictionary.util

import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import com.prototype.urbandictionary.R
import com.prototype.urbandictionary.test.TestApp
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
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
abstract class FragmentTest<TestFragment : Fragment> : InjectableTest() {
    private lateinit var fragmentScenario: FragmentScenario<TestFragment>

    @Before
    fun setUp() {
        initCommons()

        runBlocking {
            injectFragment()
        }

        fragmentScenario = FragmentScenario.launchInContainer(
            assignClass(),
            buildArguments(),
            R.style.Theme_UrbanDictionary,
            null
        )
    }

    abstract fun assignClass(): Class<TestFragment>

    open fun buildArguments(): Bundle {
        return Bundle()
    }

    abstract suspend fun injectFragment()

    open fun transformFragment(fragment: TestFragment): TestFragment {
        return fragment
    }

    private fun fragmentTest(
        isCoroutine: Boolean,
        action: TestFragment.() -> Unit = {},
        blockingAction: suspend TestFragment.() -> Unit = {}
    ) = syncAndThen {
        fragmentScenario.onFragment {
            if (isCoroutine) {
                runBlocking { blockingAction(transformFragment(it)) }
            } else {
                action(transformFragment(it))
            }
        }
    }

    fun safeTest(action: TestFragment.() -> Unit) {
        fragmentTest(false, action = action)
    }

    inline fun <reified TestFragment : Fragment> makeClass(): Class<TestFragment> {
        return TestFragment::class.java
    }

    inline fun syncAndThen(action: () -> Unit) {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        action.invoke()
    }
}