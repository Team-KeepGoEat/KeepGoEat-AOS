package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.HomeContent
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.presentation.type.HomeGoalViewType

@Serializable
data class ResponseHome(
    val data: HomeData,
    val message: String,
    val status: Int,
    val success: Boolean,
) {
    @Serializable
    data class HomeData(
        val cheeringMessage: String,
        val daytime: Int,
        val goalCount: Int,
        val goals: List<Goal>,
    ) {
        @Serializable
        data class Goal(
            val food: String,
            val criterion: String,
            val goalId: Int,
            val isAchieved: Boolean,
            val isMore: Boolean,
            val isOngoing: Boolean,
            val keptAt: String,
            val startedAt: String,
            val thisMonthCount: Int,
            val totalCount: Int,
            val writerId: Int,
        )

        fun toHomeGoal() = HomeContent(
            cheeringMessage.replace("\\n", "\n"),
            goals.map { goal ->
                HomeGoal(
                    goal.goalId,
                    "${goal.food} ${goal.criterion}",
                    goal.isMore,
                    goal.isAchieved,
                    goal.thisMonthCount,
                    HomeGoalViewType.MY_GOAL_TYPE
                )
            }
        )
    }
}
