package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.presentation.detail.GoalDetailActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingActivity

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var goalAdapter: HomeMyGoalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        addObservers()
    }

    private fun addListeners() {
        binding.btnNoGoal.setOnClickListener {
            showMakeGoalDialog()
        }
    }

    private fun initLayout() {
        goalAdapter = HomeMyGoalAdapter(::changeGoalItemBtnColor, ::changeActivityToDetail)
        binding.rvMyGoals.adapter = goalAdapter
    }

    private fun addObservers() {
        viewModel.goalList.observe(this) { goalList ->
            goalAdapter.submitList(goalList.toMutableList())
        }
    }

    private fun showMakeGoalDialog() {
        HomeBottomDialogFragment().show(supportFragmentManager, "homeDialog")
    }

    private fun changeActivityToDetail(eatingType: EatingType) {
        val intent = Intent(this@HomeActivity, GoalDetailActivity::class.java)
        intent.putExtra(GoalDetailActivity.ARG_EATING_TYPE, eatingType.name)
        startActivity(intent)
    }

    private fun changeGoalItemBtnColor(myGoal: MyGoalInfo) {
        viewModel.changeGoalAchieved(myGoal)
    }
}
