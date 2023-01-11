package org.keepgoeat.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalKeepBinding
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class GoalKeepBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomGoalKeepBinding>(R.layout.dialog_bottom_goal_keep) {
    private val viewModel: GoalDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addListeners()
        addObservers()
    }

    private fun addListeners() {
        binding.tvDelete.setOnClickListener {
            showGoalDeleteDialog()
        }
    }

    private fun addObservers() {
        viewModel.keepState.observe(this) { keepState ->
            when (keepState) {
                is UiState.Success -> {
                    dismiss()
                }
                else -> {}
            }
        }
    }

    private fun showGoalDeleteDialog() {
        GoalDeleteBottomDialogFragment().show(parentFragmentManager, "goalDeleteDialog")
    }
}
