package org.keepgoeat.presentation.home

import android.os.Bundle
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.presentation.home.adapter.HomeMyGoalAdapter
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.setVisibility

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var goalAdapter: HomeMyGoalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        goalAdapter = HomeMyGoalAdapter()
        goalAdapter.submitList(mockGoalList)
        binding.rvMyGoals.adapter = goalAdapter
        binding.layoutNoGoal.setVisibility(mockGoalList.isEmpty())
        binding.rvMyGoals.setVisibility(mockGoalList.isNotEmpty())
        binding.layoutGoalInfo.setVisibility(mockGoalList.isNotEmpty())
        when (mockGoalList.size) {
            0 -> {}
            1 -> binding.tvAddMoreGoal.text = getString(R.string.home_two_more_goal)
            2 -> binding.tvAddMoreGoal.text = getString(R.string.home_one_more_goal)
            3 -> {
                binding.tvAddMoreGoal.text = getString(R.string.home_no_more_goal)
                binding.btnMakeGoal.setVisibility(false)
            }
        }
    }

    private var mockGoalList = listOf<MyGoalInfo>(
        MyGoalInfo(
            "하루 1끼 이상 야채 더 먹기",
            "8",
            true,
            true
        ),
        MyGoalInfo(
            "라면 덜 먹기",
            "8",
            false,
            true
        ),
        MyGoalInfo(
            "커피 덜 먹기",
            "30",
            false,
            false
        )
    )
}