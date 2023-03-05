package org.keepgoeat.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar
import org.keepgoeat.R
import org.keepgoeat.databinding.ViewKgeSnackbarBinding

class KGESnackbar(
    private val view: View,
    private val message: String,
    private val duration: Int,
    private val isTop: Boolean,
) {
    private val context = view.context
    private val snackbar = Snackbar.make(view, "", duration)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
    private val snackbarBinding: ViewKgeSnackbarBinding =
        DataBindingUtil.inflate<ViewKgeSnackbarBinding?>(LayoutInflater.from(context),
            R.layout.view_kge_snackbar,
            null,
            false).apply {
            tvMessage.text = message
        }
    private val layoutParams = LinearLayout.LayoutParams(snackbar.view.layoutParams)

    init {
        initializeView()
    }

    @SuppressLint("RestrictedApi")
    private fun initializeView() {
        with(snackbarLayout) {
            removeAllViews()
            if (isTop) {
                this@KGESnackbar.layoutParams.gravity = Gravity.TOP
                layoutParams = this@KGESnackbar.layoutParams
                snackbar.animationMode = ANIMATION_MODE_FADE
            }
            setBackgroundColor(Color.TRANSPARENT)
            setPadding(0, 0, 0, 0)
            addView(snackbarBinding.root, 0)
        }
    }

    fun show() {
        snackbar.show()
    }

    fun dismiss() {
        if (duration == Snackbar.LENGTH_INDEFINITE) snackbar.dismiss()
    }
}