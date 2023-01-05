package org.keepgoeat.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import java.time.LocalDate

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.isVisible = isVisible
}

@BindingAdapter(value = ["startDate", "endDate"], requireAll = false)
fun TextView.setDuration(startDate: LocalDate?, endDate: LocalDate?) {
    safeLet(startDate, endDate) { start, end ->
        text = "${start.year}. ${
            start.monthValue.toString().padStart(2, '0')
        }. ${start.dayOfMonth} ~ ${end.year}. " + "${end.monthValue.toString().padStart(2, '0')}. ${end.dayOfMonth}"
    }
}
