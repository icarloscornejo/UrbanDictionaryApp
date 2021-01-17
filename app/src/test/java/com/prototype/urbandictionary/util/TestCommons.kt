package com.prototype.urbandictionary.util

import org.junit.Assert
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.OngoingStubbing
import org.mockito.stubbing.Stubber

// This class was created by me on another project and its used on many projects at Applaudo Studios
// It's part of a mini testing package for Activities, Fragments, ViewModels and normal Classes
// The original ones can be seen on:
// Activity: https://pastebin.com/kbzFwTGe
// Fragment: https://pastebin.com/igX2CDfm
// ViewModel: https://pastebin.com/ET89redm
// Injectable: https://pastebin.com/tFJhT87k
// Common: https://pastebin.com/hf2ww3Ee

abstract class TestCommons {

    inline fun <reified Class> mock(): Class {
        return Mockito.mock(Class::class.java)
    }

    fun <T> on(methodCall: T): OngoingStubbing<T> {
        return Mockito.`when`(methodCall)
    }

    fun doAnswer(action: (invocationOnMock: InvocationOnMock) -> Unit): Stubber {
        return Mockito.doAnswer {
            action(it)
        }
    }

    fun doNothing(): Stubber {
        return Mockito.doNothing()
    }

    fun <T> spy(thing: T): T {
        return Mockito.spy(thing)
    }

    inline fun <reified Class> spy(): Class {
        return Mockito.spy(Class::class.java)
    }

    inline fun <reified Class> any(): Class = Mockito.any(Class::class.java)

    fun assertNotNull(any: Any?) = Assert.assertNotNull(any)
    fun assertEquals(expected: Any?, actual: Any?) = Assert.assertEquals(expected, actual)
    fun assertTrue(condition: Boolean) = Assert.assertTrue(condition)
    fun assertFalse(condition: Boolean) = Assert.assertFalse(condition)
}