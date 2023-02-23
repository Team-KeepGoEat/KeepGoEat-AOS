package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.MyGoal

@Serializable
data class ResponseMy(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: MyData,
) {
    @Serializable
    data class MyData(
        val goals: List<GoalInfo>,
        val goalCount: Int,
    ) {
        @Serializable
        data class GoalInfo(
            val goalId: Int,
            val food: String,
            val criterion: String,
            val isMore: Boolean,
            val isOngoing: Boolean,
            val writerId: Int,
            val totalCount: Int,
            val startedAt: String,
            val keptAt: String?,
            val isAchieved: Boolean,
        )

        fun toMyGoal() = goals.map { goal ->
            MyGoal(
                goal.goalId,
                "${goal.food} ${goal.criterion}",
                goal.isMore,
                goal.startedAt,
                goal.keptAt,
                goal.totalCount
            )
        }
    }
}
