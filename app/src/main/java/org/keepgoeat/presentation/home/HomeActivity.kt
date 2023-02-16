package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.presentation.detail.GoalDetailActivity
import org.keepgoeat.presentation.my.MyActivity
import org.keepgoeat.presentation.sign.SignActivity
import org.keepgoeat.presentation.type.EatingType
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
        with(viewModel) {
            goalList.flowWithLifecycle(lifecycle).onEach { goalList ->
                goalAdapter.submitList(goalList.toMutableList())
            }.launchIn(lifecycleScope)
            goalCount.flowWithLifecycle(lifecycle).onEach { goalCount ->
                if (goalCount > 0)
                    binding.ivHomeSnail.setImageResource(R.drawable.ic_snail_orange_cheer_right)
            }.launchIn(lifecycleScope)
            lottieState.flowWithLifecycle(lifecycle).onEach { lottieState ->
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
        }
    }

    private fun showMakeGoalDialog() {
        HomeBottomDialogFragment().show(supportFragmentManager, "homeDialog")
    }

    private fun moveToDetail(eatingType: EatingType, goalId: Int) {
        val intent = Intent(this@HomeActivity, GoalDetailActivity::class.java)
        intent.putExtra(GoalDetailActivity.ARG_EATING_TYPE, eatingType.name)
        intent.putExtra(GoalDetailActivity.ARG_GOAL_ID, goalId)
        startActivity(intent)
    }

    private fun moveToMy() {
        val intent = Intent(this@HomeActivity, MyActivity::class.java)
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
    }
}
