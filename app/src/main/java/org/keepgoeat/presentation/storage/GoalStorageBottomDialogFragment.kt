package org.keepgoeat.presentation.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalStorageBinding
import org.keepgoeat.presentation.detail.GoalDetailViewModel
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class GoalStorageBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomGoalStorageBinding>(R.layout.dialog_bottom_goal_storage) {
    private val viewModel: GoalDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.btnStorage.setOnClickListener {
            // TODO 화면 전환 처리 구현
            viewModel.keepGoal()
        }

        binding.tvDelete.setOnClickListener {
            // TODO 화면 전환 처리 구현
            viewModel.deleteGoal()
        }
    }
}
