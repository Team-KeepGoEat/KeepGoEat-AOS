package org.keepgoeat.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalContent(
    val id: Int,
    val food: String,
    val criterion: String
) : Parcelable
