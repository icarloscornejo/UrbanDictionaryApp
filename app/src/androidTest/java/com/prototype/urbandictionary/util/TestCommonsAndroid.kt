package com.prototype.urbandictionary.util

import io.mockk.mockk
import org.junit.Assert

// This is a modified TestCommons class to use on AndroidTest
// Original Commons class: https://pastebin.com/hf2ww3Ee

abstract class TestCommonsAndroid {

    inline fun <reified Class> mock(): Class = mockk()

    fun assertNotNull(any: Any?) = Assert.assertNotNull(any)
    fun assertEquals(expected: Any?, actual: Any?) = Assert.assertEquals(expected, actual)
    fun assertTrue(condition: Boolean) = Assert.assertTrue(condition)
    fun assertFalse(condition: Boolean) = Assert.assertFalse(condition)
}