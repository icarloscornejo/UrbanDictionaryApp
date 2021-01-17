package com.prototype.urbandictionary.presentation.util

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.prototype.data.util.Constants.CLOSED_BRACKET_CHAR
import com.prototype.data.util.Constants.EMPTY_CHAR
import com.prototype.data.util.Constants.OPEN_BRACKET_CHAR
import com.prototype.domain.repository.SortingMode
import com.prototype.urbandictionary.R

object BindingAdapters {

    // Used to simplify the visibility on certain views
    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun View.visibleOrGone(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    // This was used on the "clear search field text" icon, makes itself
    // visible only when there is some text available
    @JvmStatic
    @BindingAdapter("visibleIfNotEmpty")
    fun View.visibleIfNotEmpty(value: String?) {
        visibility = if (!value.isNullOrBlank()) View.VISIBLE else View.GONE
    }

    // This is intended to erase any bracket on the given string
    @JvmStatic
    @BindingAdapter("bracketlessText")
    fun TextView.bracketlessText(value: String) {
        text = value.replace(OPEN_BRACKET_CHAR, EMPTY_CHAR).replace(CLOSED_BRACKET_CHAR, EMPTY_CHAR)
    }

    // This is used on the ImageView that controls the sorting mode on the definition list
    @JvmStatic
    @BindingAdapter("sortingMode")
    fun ImageView.sortingMode(value: SortingMode) {
        setImageResource(
            when (value) {
                SortingMode.NORMAL -> R.drawable.ic_thumbs_up_down
                SortingMode.THUMBS_UP -> R.drawable.ic_thumb_up
                SortingMode.THUMBS_DOWN -> R.drawable.ic_thumb_down
            }
        )

        backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
    }

    // This is used on the TextView that shows the sorting mode on the definition list
    @JvmStatic
    @BindingAdapter("sortingMode")
    fun TextView.sortingMode(value: SortingMode) {
        setText(
            when (value) {
                SortingMode.NORMAL -> R.string.sorting_mode_normal
                SortingMode.THUMBS_UP -> R.string.sorting_mode_thumbs_up
                SortingMode.THUMBS_DOWN -> R.string.sorting_mode_thumbs_down
            }
        )
    }

}