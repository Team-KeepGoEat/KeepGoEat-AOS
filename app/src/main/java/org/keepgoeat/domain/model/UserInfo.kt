package org.keepgoeat.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val name: String,
    val email: String,
    val archivedGoalCount: Int,
) : Parcelable
