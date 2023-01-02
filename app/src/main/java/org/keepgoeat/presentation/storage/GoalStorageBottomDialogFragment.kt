package org.keepgoeat.presentation.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomGoalStorageBinding
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

@AndroidEntryPoint
class GoalStorageBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomGoalStorageBinding>(R.layout.dialog_bottom_goal_storage) {
    private val viewModel: GoalStorageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
