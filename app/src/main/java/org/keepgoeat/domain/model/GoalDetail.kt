package org.keepgoeat.domain.model

import org.keepgoeat.presentation.type.EatingType

data class GoalDetail(
    val id: Int,
    val eatingType: EatingType,
    val thisMonthCount: Int,
    val lastMonthCount: Int,
    val goalTitle: String,
)
