package org.keepgoeat.util.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration).show()
}
