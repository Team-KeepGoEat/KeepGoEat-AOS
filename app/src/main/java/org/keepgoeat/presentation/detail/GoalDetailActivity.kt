package org.keepgoeat.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalDetailBinding
import org.keepgoeat.presentation.detail.GoalDetailViewModel.Companion.CELL_COUNT
import org.keepgoeat.presentation.storage.GoalStorageBottomDialogFragment
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.RecyclerLayoutType
import org.keepgoeat.util.ItemDecorationUtil
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.safeValueOf

@AndroidEntryPoint
class GoalDetailActivity : BindingActivity<ActivityGoalDetailBinding>(R.layout.activity_goal_detail) {
    private val viewModel: GoalDetailViewModel by viewModels()
    private lateinit var adapter: GoalStickerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        intent.getStringExtra(ARG_EATING_TYPE)?.let { strExtra ->
            val eatingType = safeValueOf<EatingType>(strExtra) ?: return@let
            viewModel.setEatingType(eatingType)
            adapter = GoalStickerListAdapter(eatingType, CELL_COUNT)
        }

        viewModel.fetchGoalDetailInfo()

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.rvGoalCard.adapter = adapter
        binding.rvGoalCard.addItemDecoration(
            ItemDecorationUtil(CARD_ITEM_SPACE, Pair(CARD_MATRIX_ROW, CARD_MATRIX_COL), RecyclerLayoutType.GRID)
        )
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivStorage.setOnClickListener {
            showGoalStorageDialog()
        }
    }

    private fun addObservers() {
        viewModel.goalStickers.observe(this) { stickers ->
            adapter.submitList(stickers)
        }
    }

    private fun showGoalStorageDialog() {
        GoalStorageBottomDialogFragment().show(supportFragmentManager, "GoalStorageDialog")
    }

    companion object {
        private const val CARD_ITEM_SPACE = 2
        private const val CARD_MATRIX_ROW = 5
        private const val CARD_MATRIX_COL = 7
        const val ARG_EATING_TYPE = "eatingType"
    }
}
