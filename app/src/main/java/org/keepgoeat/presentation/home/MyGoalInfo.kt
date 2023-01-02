package org.keepgoeat.presentation.home

@kotlinx.serialization.Serializable
data class MyGoalInfo(
    val goalName: String,
    val goalDate: String,
    val moreGoal: Boolean,
    var goalAchieved: Boolean,
    var type: Int
) {
    companion object {
        const val MY_GOAL_TYPE = 0
        const val ADD_GOAL_TYPE = 1
    }
}
