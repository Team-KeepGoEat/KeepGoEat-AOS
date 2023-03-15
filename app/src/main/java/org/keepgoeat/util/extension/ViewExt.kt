package org.keepgoeat.util.extension

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration).show()
}

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit,
) {
    var previousClickedTime = 0L
    setOnClickListener { view ->
        val clickedTime = System.currentTimeMillis()
        if (clickedTime - previousClickedTime >= delay) {
            block(view)
            previousClickedTime = clickedTime
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.addKeyboardInsetListener(keyboardCallback: ((Boolean) -> Unit)) {
    doOnLayout {
        var keyboardVisible = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
        setOnApplyWindowInsetsListener { _, windowInsets ->
            val keyboardUpdateCheck =
                rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
            if (keyboardUpdateCheck != keyboardVisible) {
                keyboardCallback(keyboardUpdateCheck)
                keyboardVisible = keyboardUpdateCheck
            }
            windowInsets
        }
    }
}
