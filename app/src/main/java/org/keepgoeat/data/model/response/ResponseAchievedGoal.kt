package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.presentation.type.EatingType

@Serializable
data class ResponseAchievedGoal(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ResponseAchievedGoalData,
) {
    @Serializable
    data class ResponseAchievedGoalData(
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
            AchievedGoal(
                goal.goalId,
                goal.food,
                if (goal.isMore) EatingType.MORE else EatingType.LESS,
                goal.startedAt,
                goal.keptAt,
                goal.totalCount
            )
        }
    }
}
