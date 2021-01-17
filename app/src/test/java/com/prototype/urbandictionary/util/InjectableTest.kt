package com.prototype.urbandictionary.util

import androidx.test.core.app.ApplicationProvider
import com.prototype.urbandictionary.test.TestApp
import org.junit.After
import org.koin.core.context.stopKoin
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.test.KoinTest

// This class was created by me on another project and its used on many projects at Applaudo Studios
// It's part of a mini testing package for Activities, Fragments, ViewModels and normal Classes
// The original ones can be seen on:
// Activity: https://pastebin.com/kbzFwTGe
// Fragment: https://pastebin.com/igX2CDfm
// ViewModel: https://pastebin.com/ET89redm
// Injectable: https://pastebin.com/tFJhT87k
// Common: https://pastebin.com/hf2ww3Ee

abstract class InjectableTest: TestCommons(), KoinTest {

    lateinit var context: TestApp

    fun initCommons() {
        context = ApplicationProvider.getApplicationContext()
    }

    fun injectModule(moduleDeclaration: ModuleDeclaration) {
        context.injectModule(module(moduleDeclaration = moduleDeclaration))
    }

    @After
    fun autoClose() {
        stopKoin()
    }
}