package org.keepgoeat.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalSettingBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.model.GoalContent
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.getParcelable
import org.keepgoeat.util.extension.showKeyboard
import org.keepgoeat.util.safeValueOf

@AndroidEntryPoint
class GoalSettingActivity : BindingActivity<ActivityGoalSettingBinding>(R.layout.activity_goal_setting) {
    private val viewModel: GoalSettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        intent.let {
            it.getStringExtra(ARG_EATING_TYPE)?.let { strExtra ->
                val eatingType = safeValueOf<EatingType>(strExtra) ?: return@let
                viewModel.setEatingType(eatingType)
            }

            it.getParcelable(ARG_GOAL_CONTENT, GoalContent::class.java)?.let { goal ->
                viewModel.setGoalContent(goal)
            }
        }

        addListeners()
        addObservers()
    }

    private fun addObservers() {
        viewModel.uploadState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    moveToHome()
                }
                else -> {}
            }
        }
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            showKeyboard(it, false)
            binding.etGoal.clearFocus()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {
        const val ARG_EATING_TYPE = "eatingType"
        const val ARG_GOAL_CONTENT = "goalContent"
    }
}
