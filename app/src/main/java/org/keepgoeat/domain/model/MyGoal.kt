package org.keepgoeat.domain.model

data class MyGoal(
    val id: Int,
    val goalContent: String,
    val isMore: Boolean,
    val startedAt: String,
    val keptAt: String?,
    val totalCount: Int
)
