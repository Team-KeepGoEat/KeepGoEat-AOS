package org.keepgoeat.domain.model

import org.keepgoeat.presentation.type.HomeGoalViewType

data class HomeGoal(
    val id: Int,
    val goalTitle: String,
    val isMore: Boolean,
    val isAchieved: Boolean,
    val thisMonthCount: Int,
    val type: HomeGoalViewType
)
