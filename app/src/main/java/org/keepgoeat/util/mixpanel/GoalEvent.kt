package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent

object GoalEvent {
    fun completeGoal(goalName: String, goalStandard: String) = MixPanelEvent(
        "Complete Goal",
        mapOf("Goal Name" to goalName, "Goal Standard" to goalStandard)
    )

    fun addGoal(eatingType: String) = MixPanelEvent(
        "Add Goal",
        mapOf("Goal Type" to eatingType)
    )

    fun createGoal(title: String, criterion: String) = MixPanelEvent(
        "Create Goal",
        mapOf("Goal Name" to title, "Goal Standard" to criterion)
    )

    fun deleteGoal() = MixPanelEvent("Delete Goal", null)

    fun archiveGoal(goalName: String, goalStandard: String) = MixPanelEvent(
        "Archive",
        mapOf("Goal Name" to goalName, "Goal Standard" to goalStandard)
    )
}
