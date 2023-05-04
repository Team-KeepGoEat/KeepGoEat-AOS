package org.keepgoeat.util

import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import coil.load
import org.keepgoeat.presentation.type.HomeBackgroundType
import org.keepgoeat.util.extension.getStringLength

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.isVisible = isVisible
}

/*
@BindingAdapter(value = ["startDate", "endDate"], requireAll = false)
fun TextView.setDuration(startDate: LocalDate?, endDate: LocalDate?) {
    safeLet(startDate, endDate) { s, e ->
        text = String.format(
            context.getString(R.string.my_goal_duration_format),
            s.year,
            s.monthValue.padZero(2),
            s.dayOfMonth.padZero(2),
            e.year,
            e.monthValue.padZero(2),
            e.dayOfMonth.padZero(2)
        )
    }
}
*/

@BindingAdapter(value = ["startDate", "endDate"], requireAll = false)
fun TextView.setDurationText(startDate: String?, endDate: String?) {
    safeLet(startDate, endDate) { s, e ->
        text = "$s ~ $e"
    }
}

@BindingAdapter("homeBackground")
fun ImageView.setBackground(hour: Int) {
    val imgRes = when (hour) {
        in 7..16 -> HomeBackgroundType.MORNING.sky
        in 18..24, in 0..5 -> HomeBackgroundType.NIGHT.sky
        in 5..7, in 16..18 -> HomeBackgroundType.EVENING.sky
        else -> {
            HomeBackgroundType.MORNING.sky
        }
    }
    setBackgroundResource(imgRes)
}

/** 시스템이 인식하는 글자 수를 사용자가 인식하는 글자 수로 변환해서 글자 수 범위에 따라 maxLength 를 설정 */
@BindingAdapter("maxLen")
fun EditText.cutTextToMaxLength(maxLength: Int) {
    val filterArray = arrayOfNulls<InputFilter>(1)
    addTextChangedListener {
        val str = text.toString()
        val lengthFilter =
            if (str.getStringLength() == maxLength) str.length else Int.MAX_VALUE
        filters = filterArray.apply { this[0] = InputFilter.LengthFilter(lengthFilter) }
    }
}
