package org.keepgoeat.domain.model

import org.keepgoeat.presentation.type.EatingType
import java.time.LocalDate

data class AchievedGoal(
    val id: Int,
    val eatingType: EatingType,
    val goalTitle: String,
    val totalAchievementDays: Int,
    val startAt: LocalDate,
    val endAt: LocalDate,
)
