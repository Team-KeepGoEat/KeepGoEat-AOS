package org.keepgoeat.presentation.my.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogGoalDeleteBinding
import org.keepgoeat.presentation.my.MyViewModel
import org.keepgoeat.presentation.base.screen.BindingDialogFragment

class GoalDeleteDialogFragment :
    BindingDialogFragment<DialogGoalDeleteBinding>(R.layout.dialog_goal_delete) {
    private val viewModel: MyViewModel by activityViewModels()
    private var goalId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goalId = arguments?.getInt(ARG_GOAL_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        addListeners()
    }

    private fun addListeners() {
        binding.no.setOnClickListener {
            dismiss()
        }
        binding.yes.setOnClickListener {
            goalId?.let { goalId ->
                viewModel.deleteArchivedGoal(goalId)
            }
            dismiss()
        }
    }

    companion object {
        const val ARG_GOAL_ID = "goalId"
    }
}
