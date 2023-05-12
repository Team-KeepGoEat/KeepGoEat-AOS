package org.keepgoeat.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.presentation.type.EatingType

@Serializable
data class ResponseGoalDetail(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ResponseGoalDetailData,
) {
    @Serializable
    data class ResponseGoalDetailData(
        @SerialName("goalId")
        val id: Int,
        val isMore: Boolean,
        val thisMonthCount: Int,
        val lastMonthCount: Int,
        val blankBoxCount: Int,
        val emptyBoxCount: Int,
        val food: String,
        val criterion: String
    ) {
        fun toGoalDetail() = GoalDetail(
            id = id,
            eatingType = if (isMore) EatingType.MORE else EatingType.LESS,
            thisMonthCount = thisMonthCount,
            lastMonthCount = lastMonthCount,
            food = food,
            criterion = criterion
        )
    }
}
