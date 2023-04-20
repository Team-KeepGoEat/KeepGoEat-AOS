package org.keepgoeat.domain.model

import org.keepgoeat.presentation.type.EatingType

data class ArchivedGoal(
    val id: Int,
    val goalContent: String,
    val eatingType: EatingType,
    val startedAt: String,
    val archivedAt: String?,
    val totalCount: Int,
)
