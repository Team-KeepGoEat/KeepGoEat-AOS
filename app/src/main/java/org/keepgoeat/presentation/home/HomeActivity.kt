package org.keepgoeat.presentation.home

import android.os.Bundle
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.presentation.home.adapter.HomeMyGoalAdapter
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
        goalAdapter = HomeMyGoalAdapter(::changeGoalItemBtnColor)
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

    private fun changeGoalItemBtnColor(myGoal: MyGoalInfo) {
        viewModel.changeGoalAchieved(myGoal)
    }
}
