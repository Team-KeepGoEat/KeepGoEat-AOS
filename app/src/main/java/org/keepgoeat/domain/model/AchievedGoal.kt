package org.keepgoeat.domain.model

data class AchievedGoal(
    val id: Int,
    val goalContent: String,
    val isMore: Boolean,
    val startedAt: String,
    val keptAt: String?,
    val totalCount: Int,
)
