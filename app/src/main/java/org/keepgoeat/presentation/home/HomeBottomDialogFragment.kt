package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.keepgoeat.R
import org.keepgoeat.databinding.DialogBottomHomeBinding
import org.keepgoeat.presentation.setting.GoalSettingActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingBottomSheetDialogFragment

class HomeBottomDialogFragment :
    BindingBottomSheetDialogFragment<DialogBottomHomeBinding>(R.layout.dialog_bottom_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.layoutHomeBottomMore.setOnClickListener {
            changeActivity(EatingType.MORE)
        }
        binding.layoutHomeBottomLess.setOnClickListener {
            changeActivity(EatingType.LESS)
        }
    }

    private fun changeActivity(eatingType: EatingType) {
        val intent = Intent(requireActivity(), GoalSettingActivity::class.java)
        intent.putExtra(GoalSettingActivity.ARG_EATING_TYPE, eatingType.name)
        startActivity(intent)
    }
}
