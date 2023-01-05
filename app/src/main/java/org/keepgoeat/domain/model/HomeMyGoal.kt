package org.keepgoeat.domain.model

import org.keepgoeat.presentation.type.HomeGoalViewType

data class HomeMyGoal(
    val goalId: Int,
    val goalContent: String,
    val isMore: Boolean,
    val isAchieved: Boolean,
    val thisMonthCount: Int,
    val type: HomeGoalViewType
)
