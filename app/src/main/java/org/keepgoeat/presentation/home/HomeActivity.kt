package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.presentation.detail.GoalDetailActivity
import org.keepgoeat.presentation.my.AchievedGoalActivity
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

        initLayout()
        addListeners()
        addObservers()
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

    private fun addObservers() {
        viewModel.goalList.observe(this) { goalList -> // EventObserver
            goalAdapter.submitList(goalList.toMutableList())
        }
        viewModel.goalCount.observe(this) { goalCount ->
            if (goalCount == 0)
                binding.ivHomeSnail.setImageResource(R.drawable.ic_snail_orange_hungry)
        }
        viewModel.achievedState.observe(this) { isAchieved ->
            if (isAchieved) {
                binding.lottieSnail.playAnimation()
                binding.lottieBackground.playAnimation()
            }
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
        val intent = Intent(this@HomeActivity, AchievedGoalActivity::class.java)
        startActivity(intent)
    }

    private fun changeGoalItemBtnColor(myGoal: HomeGoal) {
        viewModel.changeGoalAchieved(myGoal)
    }
}
