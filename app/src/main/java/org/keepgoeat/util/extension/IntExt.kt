package org.keepgoeat.util.extension

import android.content.res.Resources
import kotlin.math.roundToInt

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

fun Int.padZero(length: Int) = toString().padStart(length, '0')
