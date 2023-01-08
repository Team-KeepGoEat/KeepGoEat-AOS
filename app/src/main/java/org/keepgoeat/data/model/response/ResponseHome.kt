package org.keepgoeat.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.HomeMyGoal
import org.keepgoeat.presentation.type.HomeGoalViewType

@Serializable
data class ResponseHome(
    @SerialName("data")
    val homeData: HomeData,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class HomeData(
        val cheeringMessage: String,
        val daytime: Int,
        val goalCount: Int,
        val goals: List<Goal>
    ) {
        @Serializable
        data class Goal(
            val goalContent: String,
            val goalId: Int,
            val isAchieved: Boolean,
            val isMore: Boolean,
            val isOngoing: Boolean,
            val keptAt: String,
            val startedAt: String,
            val thisMonthCount: Int,
            val totalCount: Int,
            val writerId: Int
        ) {
            fun toHomeMyGoal(goal: Goal): HomeMyGoal =
                HomeMyGoal(
                    goal.goalId,
                    goal.goalContent,
                    goal.isMore,
                    goal.isAchieved,
                    goal.thisMonthCount,
                    HomeGoalViewType.MY_GOAL_TYPE
                )
        }
    }
}
