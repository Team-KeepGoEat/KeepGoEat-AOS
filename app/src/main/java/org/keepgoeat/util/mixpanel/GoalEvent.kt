package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent

object GoalEvent {
    fun completeGoal(goalName: String, goalStandard: String) = MixPanelEvent(
        "Complete Goal",
        mapOf("Goal Name" to goalName, "Goal Standard" to goalStandard)
    )
}
