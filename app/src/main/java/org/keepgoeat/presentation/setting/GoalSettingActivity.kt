package org.keepgoeat.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalSettingBinding
import org.keepgoeat.presentation.detail.GoalDetailActivity
import org.keepgoeat.presentation.detail.GoalDetailActivity.Companion.ARG_GOAL_ID
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.model.GoalContent
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.UiState
import org.keepgoeat.presentation.base.screen.BindingActivity
import org.keepgoeat.util.extension.getParcelable
import org.keepgoeat.util.extension.setOnSingleClickListener
import org.keepgoeat.util.extension.showKeyboard
import org.keepgoeat.util.extension.showToast
import org.keepgoeat.util.safeValueOf

@AndroidEntryPoint
class GoalSettingActivity :
    BindingActivity<ActivityGoalSettingBinding>(R.layout.activity_goal_setting) {
    private val viewModel: GoalSettingViewModel by viewModels()
    private var isEditMode: Boolean = false

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
                isEditMode = true
                viewModel.setGoalContent(goal)
            }
        }

        addListeners()
        collectData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(viewModel.eatingType.value?.name?.lowercase() ?: "")
    }

    private fun collectData() {
        viewModel.uploadState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    if (isEditMode) {
                        showToast(getString(R.string.goal_setting_edit_success_toast_message))
                        moveToDetail()
                    } else {
                        showToast(getString(R.string.goal_setting_add_success_toast_message))
                        moveToHome()
                    }
                }
                is UiState.Error -> {}
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            showKeyboard(it, false)
            binding.etGoal.clearFocus()
            binding.etGoalCriterion.clearFocus()
        }
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.btnComplete.setOnSingleClickListener {
            showKeyboard(it, false)
            viewModel.uploadGoal()
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun moveToDetail() {
        val intent = Intent(this, GoalDetailActivity::class.java).apply {
            putExtra(ARG_GOAL_ID, viewModel.goalId)
            putExtra(ARG_IS_UPDATED, true)
            putExtra(ARG_EATING_TYPE, viewModel.eatingType.value?.name)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {
        const val ARG_EATING_TYPE = "eatingType"
        const val ARG_GOAL_CONTENT = "goalContent"
        const val ARG_IS_UPDATED = "isUpdated"
    }
}
