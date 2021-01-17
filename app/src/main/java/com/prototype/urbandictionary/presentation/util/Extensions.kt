package com.prototype.urbandictionary.presentation.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

// Returns the InputMethodManager
fun View.inputMethodManager(): InputMethodManager {
    return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}

// Shows the keyboard
fun View.showKeyBoard() {
    inputMethodManager().showSoftInput(this, 0)
}

// Hides the keyboard
fun View.hideKeyBoard() {
    inputMethodManager().hideSoftInputFromWindow(this.windowToken, 0)
}

// Sets an observer on a certaing LiveData, it's just a shortcut
inline fun <Type> LiveData<Type?>.setObserver(
    owner: LifecycleOwner,
    crossinline action: (Type) -> Unit
) {
    observe(owner, { value -> value?.let { action(it) } })
}