package org.keepgoeat.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalKeepBinding
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class GoalKeepBottomDialogFragment : // TODO 네이밍 수정 keep -> archive
    BindingBottomSheetDialogFragment<DialogBottomGoalKeepBinding>(R.layout.dialog_bottom_goal_keep) {
    private val viewModel: GoalDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addListeners()
        collectData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(SCREEN_NAME)
    }

    private fun addListeners() {
        binding.tvDelete.setOnClickListener {
            showGoalDeleteDialog()
        }
    }

    private fun collectData() {
        viewModel.keepState.flowWithLifecycle(lifecycle).onEach { keepState ->
            when (keepState) {
                is UiState.Success -> {
                    dismiss()
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun showGoalDeleteDialog() {
        GoalDeleteBottomDialogFragment().show(parentFragmentManager, "goalDeleteDialog")
        dismiss()
    }

    companion object {
        private const val SCREEN_NAME = "goal archive"
    }
}
