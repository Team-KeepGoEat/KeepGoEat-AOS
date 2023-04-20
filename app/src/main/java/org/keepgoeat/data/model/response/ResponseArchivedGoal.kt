package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.ArchivedGoal
import org.keepgoeat.presentation.type.EatingType

@Serializable
data class ResponseArchivedGoal(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ResponseArchivedGoalData,
) {
    @Serializable
    data class ResponseArchivedGoalData(
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
            ArchivedGoal(
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
