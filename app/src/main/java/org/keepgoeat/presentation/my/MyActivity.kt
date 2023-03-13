package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class MyActivity : BindingActivity<ActivityMyBinding>(R.layout.activity_my) {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.tvUserName.setOnClickListener {
            startActivity(Intent(this, AccountInfoActivity::class.java))
        }
        binding.tvAchievedGoal.setOnClickListener {
            moveToAchievedGoalDetail()
        }
        binding.tvAchievedGoalCount.setOnClickListener {
            moveToAchievedGoalDetail()
        }
        binding.ivAchievedGoalDetail.setOnClickListener {
            moveToAchievedGoalDetail()
        }
        binding.tvAboutService.setOnClickListener {
            startActivity(Intent(this, ServiceIntroActivity::class.java))
        }
    }

    private fun moveToAchievedGoalDetail() {
        startActivity(Intent(this, AchievedGoalActivity::class.java))
    }

    private fun collectData() {
        // TODO 보관한 목표 갯수 불러오기
    }
}
