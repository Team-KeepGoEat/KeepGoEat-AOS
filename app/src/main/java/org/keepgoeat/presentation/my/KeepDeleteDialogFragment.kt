package org.keepgoeat.presentation.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogKeepDeleteBinding
import org.keepgoeat.util.binding.BindingDialogFragment

class KeepDeleteDialogFragment :
    BindingDialogFragment<DialogKeepDeleteBinding>(R.layout.dialog_keep_delete) {
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
                viewModel.deleteGoal(goalId)
            }
            dismiss()
        }
    }

    companion object {
        const val ARG_GOAL_ID = "goalId"
    }
}
