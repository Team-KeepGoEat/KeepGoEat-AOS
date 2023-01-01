package org.keepgoeat.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalDetailBinding
import org.keepgoeat.presentation.detail.GoalDetailViewModel.Companion.CELL_COUNT
import org.keepgoeat.presentation.type.EatingType
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

        val eatingType = EatingType.MORE // TODO intent로 argument 전달받기
        viewModel.setEatingType(eatingType)
        adapter = GoalStickerListAdapter(eatingType, CELL_COUNT)

        initLayout()
        addObservers()
    }

    private fun initLayout() {
        binding.rvGoalCard.adapter = adapter
        binding.rvGoalCard.addItemDecoration(
            ItemDecorationUtil(CARD_ITEM_SPACE, Pair(CARD_MATRIX_ROW, CARD_MATRIX_COL), RecyclerLayoutType.GRID)
        )
    }

    private fun addListeners() {
        TODO("Not yet implemented")
    }

    private fun addObservers() {
        viewModel.goalStickers.observe(this) { stickers ->
            adapter.submitList(stickers)
        }
    }

    companion object {
        private const val CARD_ITEM_SPACE = 2
        private const val CARD_MATRIX_ROW = 5
        private const val CARD_MATRIX_COL = 7
    }
}
