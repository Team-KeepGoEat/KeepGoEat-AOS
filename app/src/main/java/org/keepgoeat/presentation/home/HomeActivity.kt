package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.presentation.detail.GoalDetailActivity
import org.keepgoeat.presentation.my.MyActivity
import org.keepgoeat.presentation.sign.SignActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.ProcessState
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var goalAdapter: HomeGoalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (intent.getBooleanExtra(ARG_KILL_HOME_AND_GO_TO_SIGN, false)) moveToSign()

        initLayout()
        addListeners()
        collectData()
    }

    private fun addListeners() {
        with(binding) {
            btnNoGoal.setOnClickListener {
                showMakeGoalDialog()
            }
            ivMyPage.setOnClickListener {
                moveToMy()
            }
        }
    }

    private fun initLayout() {
        goalAdapter =
            HomeGoalAdapter(::changeGoalItemBtnColor, ::moveToDetail, ::showMakeGoalDialog)
        binding.rvMyGoals.apply {
            itemAnimator = null
            adapter = goalAdapter
        }
    }

    private fun collectData() {
        viewModel.goalList.flowWithLifecycle(lifecycle).onEach { goalList ->
            goalAdapter.submitList(goalList.toMutableList())
        }.launchIn(lifecycleScope)
        viewModel.goalCount.flowWithLifecycle(lifecycle).onEach { goalCount ->
            if (goalCount > 0)
                binding.ivHomeSnail.setImageResource(R.drawable.ic_snail_orange_cheer_right)
        }.launchIn(lifecycleScope)
        viewModel.lottieState.flowWithLifecycle(lifecycle).onEach { lottieState ->
            when (lottieState) {
                ProcessState.IN_PROGRESS -> {
                    binding.lottieSnail.playAnimation()
                    binding.lottieBackground.playAnimation()
                    viewModel.changeLottieState(ProcessState.DONE)
                }
                ProcessState.IDLE -> {}
                ProcessState.DONE -> {}
            }
        }.launchIn(lifecycleScope)
        combine(viewModel.homeDataFetchState, isConnectedNetwork) { fetchState, isConnected ->
            fetchState !is UiState.Success && isConnected
        }.flowWithLifecycle(lifecycle).onEach { shouldFetch ->
            if (shouldFetch) viewModel.fetchHomeContent()
        }.launchIn(lifecycleScope)
        viewModel.updateVersion.flowWithLifecycle(lifecycle).onEach { updateVersion ->
            if (!updateVersion.isNullOrBlank() && viewModel.compareVersion(updateVersion))
                showForceUpdateDialog(updateVersion)
        }.launchIn(lifecycleScope)
    }

    private fun showMakeGoalDialog() {
        HomeBottomDialogFragment().show(supportFragmentManager, "homeDialog")
    }

    private fun showForceUpdateDialog(updateVersion: String) {
        HomeForceUpdateDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_UPDATE_VERSION, updateVersion)
            }
        }.show(supportFragmentManager, "forceUpdateDialog")
    }

    private fun moveToDetail(eatingType: EatingType, goalId: Int) {
        val intent = Intent(this@HomeActivity, GoalDetailActivity::class.java)
        intent.putExtra(GoalDetailActivity.ARG_EATING_TYPE, eatingType.name)
        intent.putExtra(GoalDetailActivity.ARG_GOAL_ID, goalId)
        intent.putExtra(ARG_HOME_GOAL_COUNT, viewModel.goalCount.value)
        startActivity(intent)
    }

    private fun moveToMy() {
        val intent = Intent(this@HomeActivity, MyActivity::class.java)
        intent.putExtra(ARG_HOME_GOAL_COUNT, viewModel.goalCount.value)
        startActivity(intent)
    }

    private fun changeGoalItemBtnColor(myGoal: HomeGoal) {
        viewModel.changeGoalAchieved(myGoal)
    }

    private fun moveToSign() {
        startActivity(Intent(this, SignActivity::class.java))
        finish()
    }

    companion object {
        const val ARG_KILL_HOME_AND_GO_TO_SIGN = "killHomeAndGoToSign"
        const val ARG_HOME_GOAL_COUNT = "homeGoalCount"
        const val ARG_UPDATE_VERSION = "updateVersion"
    }
}
