package org.keepgoeat.presentation.setting

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalSettingBinding
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.safeValueOf

@AndroidEntryPoint
class GoalSettingActivity : BindingActivity<ActivityGoalSettingBinding>(R.layout.activity_goal_setting) {
    private val viewModel: GoalSettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        intent.getStringExtra(ARG_EATING_TYPE)?.let { strExtra ->
            val eatingType = safeValueOf<EatingType>(strExtra) ?: return@let
            viewModel.setEatingType(eatingType)
        }

        addListeners()
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ARG_EATING_TYPE = "eatingType"
    }
}
