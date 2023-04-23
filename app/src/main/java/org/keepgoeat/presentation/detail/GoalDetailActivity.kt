package org.keepgoeat.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityGoalDetailBinding
import org.keepgoeat.presentation.detail.GoalDetailViewModel.Companion.CELL_COUNT
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.model.GoalContent
import org.keepgoeat.presentation.my.archive.ArchivedGoalActivity
import org.keepgoeat.presentation.my.archive.ArchivedGoalActivity.Companion.ARG_IS_ENTERED_FROM_KEEP
import org.keepgoeat.presentation.setting.GoalSettingActivity
import org.keepgoeat.presentation.setting.GoalSettingActivity.Companion.ARG_GOAL_CONTENT
import org.keepgoeat.presentation.setting.GoalSettingActivity.Companion.ARG_IS_UPDATED
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.RecyclerLayoutType
import org.keepgoeat.util.ItemDecorationUtil
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.showToast
import org.keepgoeat.util.safeValueOf

@AndroidEntryPoint
class GoalDetailActivity :
    BindingActivity<ActivityGoalDetailBinding>(R.layout.activity_goal_detail) {
    private val viewModel: GoalDetailViewModel by viewModels()
    private lateinit var adapter: GoalStickerListAdapter
    private var isUpdated = false
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            moveToHome()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        intent.let {
            val eatingType = safeValueOf<EatingType>(it.getStringExtra(ARG_EATING_TYPE)) ?: return
            binding.eatingType = eatingType
            adapter = GoalStickerListAdapter(eatingType, CELL_COUNT)
            val goalId = it.getIntExtra(ARG_GOAL_ID, -1)
            viewModel.fetchGoalDetailInfo(goalId)
            isUpdated = it.getBooleanExtra(ARG_IS_UPDATED, false)
            if (isUpdated) this.onBackPressedDispatcher.addCallback(this, callback)
        }

        initLayout()
        addListeners()
        collectData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(SCREEN_NAME)
    }

    private fun initLayout() {
        binding.rvGoalCard.apply {
            addItemDecoration(
                ItemDecorationUtil(
                    CARD_ITEM_SPACE,
                    Pair(CARD_MATRIX_ROW, CARD_MATRIX_COL),
                    RecyclerLayoutType.GRID
                )
            )
            clipToOutline = true
            adapter = this@GoalDetailActivity.adapter
        }
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            if (isUpdated) moveToHome()
            else finish()
        }
        binding.ivKeep.setOnClickListener {
            showGoalKeepDialog()
        }
        binding.ivEdit.setOnClickListener {
            viewModel.goalDetail.value?.let { detail ->
                val content = GoalContent(detail.id, detail.food, detail.criterion)
                Intent(this, GoalSettingActivity::class.java).apply {
                    putExtra(ARG_GOAL_CONTENT, content)
                    putExtra(ARG_EATING_TYPE, detail.eatingType.name)
                }.also {
                    startActivity(it)
                }
            }
        }
    }

    private fun collectData() {
        viewModel.goalStickers.flowWithLifecycle(lifecycle).onEach { stickers ->
            adapter.submitList(stickers)
        }.launchIn(lifecycleScope)
        viewModel.keepState.flowWithLifecycle(lifecycle).onEach { keepState ->
            when (keepState) {
                is UiState.Success -> {
                    showToast(getString(R.string.goal_detail_success_goal_keep_toast_message))
                    Intent(this, ArchivedGoalActivity::class.java).apply {
                        putExtra(ARG_IS_ENTERED_FROM_KEEP, true)
                        putExtra(
                            ARG_HOME_GOAL_COUNT,
                            intent.getIntExtra(ARG_HOME_GOAL_COUNT, -1) - 1
                        )
                    }.also {
                        startActivity(it)
                        finish()
                    }
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
        viewModel.deleteState.flowWithLifecycle(lifecycle).onEach { deleteState ->
            when (deleteState) {
                is UiState.Success -> {
                    showToast(getString(R.string.goal_detail_success_goal_delete_toast_message))
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun showGoalKeepDialog() {
        intent?.let {
            GoalKeepBottomDialogFragment().show(supportFragmentManager, "goalKeepDialog")
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {
        private const val CARD_ITEM_SPACE = 2
        private const val CARD_MATRIX_ROW = 5
        private const val CARD_MATRIX_COL = 7
        const val ARG_EATING_TYPE = "eatingType"
        const val ARG_GOAL_ID = "goalId"
        const val ARG_HOME_GOAL_COUNT = "homeGoalCount"
        private const val SCREEN_NAME = "goal detail"
    }
}
