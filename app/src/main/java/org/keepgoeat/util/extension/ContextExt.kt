package org.keepgoeat.util.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.showToast(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(this, message, duration).show()
}

fun Context.showKeyboard(view: View, toShow: Boolean) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        if (toShow) showSoftInput(view, 0)
        else hideSoftInputFromWindow(view.windowToken, 0)
    }
}
