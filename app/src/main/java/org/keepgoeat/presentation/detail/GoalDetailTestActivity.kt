package org.keepgoeat.presentation.detail

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalDetailTestBinding
import org.keepgoeat.presentation.detail.GoalDetailActivity.Companion.ARG_EATING_TYPE
import org.keepgoeat.presentation.setting.GoalSettingActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingActivity

// TODO 더먹기 및 덜먹기 카드 상세뷰 확인을 위한 테스트 액티비티로, 뷰 구현 마칠 때 해당 파일 삭제 예정
@AndroidEntryPoint
class GoalDetailTestActivity : BindingActivity<ActivityGoalDetailTestBinding>(R.layout.activity_goal_detail_test) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.viewEatingLessTag.root.setOnClickListener {
            moveToGoalDetail(EatingType.LESS)
        }
        binding.viewEatingMoreTag.root.setOnClickListener {
            moveToGoalDetail(EatingType.MORE)
        }
    }

    private fun moveToGoalDetail(eatingType: EatingType) {
        Intent(this, GoalSettingActivity::class.java).apply {
            putExtra(ARG_EATING_TYPE, eatingType.name)
        }.also {
            startActivity(it)
        }
    }
}
