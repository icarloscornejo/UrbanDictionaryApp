package com.prototype.urbandictionary.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.prototype.data.util.Constants
import com.prototype.domain.model.DefinitionModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class DefinitionViewModel(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val definition: DefinitionModel
) : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val wordField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val definitionField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val exampleField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val authorField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val dateField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val thumbsUpField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val thumbsDownField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val apiDateFormat by lazy {
        SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.getDefault())
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val appDateFormat by lazy {
        SimpleDateFormat(Constants.APP_DATE_FORMAT, Locale.getDefault())
    }

    init {
        with(definition) {
            wordField.set(word)
            definitionField.set(definition)
            exampleField.set(example)
            authorField.set(author)
            dateField.set(getReadableDate(writtenOn))
            thumbsUpField.set("$thumbsUp")
            thumbsDownField.set("$thumbsDown")
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getReadableDate(apiDate: String): String {
        if (apiDate.isBlank()) return Constants.HYPHEN
        return appDateFormat.format(apiDateFormat.parse(apiDate) ?: Date())
    }
}