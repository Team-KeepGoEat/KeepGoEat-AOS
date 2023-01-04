package org.keepgoeat.presentation.home

import kotlinx.serialization.Serializable
import org.keepgoeat.presentation.type.HomeGoalViewType

@Serializable
data class MyGoalInfo(
    val goalName: String,
    val goalDate: String,
    val moreGoal: Boolean,
    var goalAchieved: Boolean,
    var type: HomeGoalViewType
)
