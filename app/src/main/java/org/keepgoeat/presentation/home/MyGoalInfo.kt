package org.keepgoeat.presentation.home

@kotlinx.serialization.Serializable
data class MyGoalInfo(
    val goalName: String,
    val goalDate: String,
    val moreGoal: Boolean,
    var goalAchieved: Boolean
)
