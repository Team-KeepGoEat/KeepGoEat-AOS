package org.keepgoeat.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalDetailBinding
import org.keepgoeat.presentation.detail.GoalDetailViewModel.Companion.CELL_COUNT
import org.keepgoeat.presentation.type.RecyclerLayoutType
import org.keepgoeat.util.ItemDecorationUtil
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class GoalDetailActivity : BindingActivity<ActivityGoalDetailBinding>(R.layout.activity_goal_detail) {
    private val viewModel: GoalDetailViewModel by viewModels()
    private lateinit var adapter: GoalStickerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (intent.hasExtra(ARG_GOAL_ID)) {
            val goalId = intent.getIntExtra(ARG_GOAL_ID, -1)
            viewModel.fetchGoalDetailInfo(goalId)
        }

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.rvGoalCard.addItemDecoration(
            ItemDecorationUtil(CARD_ITEM_SPACE, Pair(CARD_MATRIX_ROW, CARD_MATRIX_COL), RecyclerLayoutType.GRID)
        )
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivKeep.setOnClickListener {
            showGoalKeepDialog()
        }
    }

    private fun addObservers() {
        viewModel.goalDetail.observe(this) { detail -> // TODO 리팩토링 필요
            adapter = GoalStickerListAdapter(detail.eatingType, CELL_COUNT)
            binding.rvGoalCard.adapter = adapter
        }
        viewModel.goalStickers.observe(this) { stickers ->
            adapter.submitList(stickers)
        }
    }

    private fun showGoalKeepDialog() {
        GoalKeepBottomDialogFragment().show(supportFragmentManager, "goalKeepDialog")
    }

    companion object {
        private const val CARD_ITEM_SPACE = 2
        private const val CARD_MATRIX_ROW = 5
        private const val CARD_MATRIX_COL = 7
        const val ARG_EATING_TYPE = "eatingType"
        const val ARG_GOAL_ID = "goalId"
    }
}
