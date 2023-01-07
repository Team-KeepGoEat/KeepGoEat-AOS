package org.keepgoeat.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityHomeBinding
import org.keepgoeat.presentation.detail.GoalDetailActivity
import org.keepgoeat.presentation.my.MyActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.setBackground
import java.time.LocalDateTime

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
        with(binding) {
            btnNoGoal.setOnClickListener {
                showMakeGoalDialog()
            }
            ivMyPage.setOnClickListener {
                changeActivityToMyPage()
            }
        }
    }

    private fun initLayout() {
        goalAdapter = HomeMyGoalAdapter(::changeGoalItemBtnColor, ::changeActivityToDetail)
        binding.rvMyGoals.apply {
            itemAnimator = null
            adapter = goalAdapter
        }
        binding.ivHomeBackground.setBackground(LocalDateTime.now().hour)
    }

    private fun addObservers() {
        viewModel.goalList.observe(this) { goalList ->
            goalAdapter.submitList(goalList.toMutableList())
        }
        viewModel.goalCount.observe(this) { goalCount ->
            if (goalCount == 0) {
                binding.ivHomeSnail.setImageResource(R.drawable.img_snail_orange_hungry)
            }
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

    private fun changeActivityToMyPage() {
        val intent = Intent(this@HomeActivity, MyActivity::class.java)
        startActivity(intent)
    }

    private fun changeGoalItemBtnColor(myGoal: MyGoalInfo) {
        viewModel.changeGoalAchieved(myGoal)
    }
}
