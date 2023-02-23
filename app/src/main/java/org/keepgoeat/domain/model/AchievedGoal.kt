package org.keepgoeat.domain.model

import org.keepgoeat.presentation.type.EatingType

data class AchievedGoal(
    val id: Int,
    val goalContent: String,
    val eatingType: EatingType,
    val startedAt: String,
    val keptAt: String?,
    val totalCount: Int,
)
