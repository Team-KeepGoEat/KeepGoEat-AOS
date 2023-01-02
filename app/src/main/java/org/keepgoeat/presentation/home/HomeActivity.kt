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
        with(binding) {
            rvMyGoals.adapter = goalAdapter
            layoutNoGoal.setVisibility(mockGoalList.isEmpty())
            rvMyGoals.setVisibility(mockGoalList.isNotEmpty())
            layoutGoalInfo.setVisibility(mockGoalList.isNotEmpty())
        }
        when (mockGoalList.size) {
            0 -> {
                binding.imgHomeSnail.setImageResource(R.drawable.img_snail_orange_hungry)
            }
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
