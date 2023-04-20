package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityArchivedGoalBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.showToast

@AndroidEntryPoint
class ArchivedGoalActivity :
    BindingActivity<ActivityArchivedGoalBinding>(R.layout.activity_archived_goal) {
    private val viewModel: MyViewModel by viewModels()
    lateinit var goalAdapter: ArchivedGoalAdapter
    private val headerAdapter = ArchivedGoalHeaderAdapter(::getFilteredGoalWithEatingType)
    lateinit var goalConcatAdapter: ConcatAdapter
    private var isEnteredFromKeep: Boolean = false

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            moveToPrevious()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        isEnteredFromKeep = intent.getBooleanExtra(ARG_IS_ENTERED_FROM_KEEP, false)

        viewModel.fetchArchivedGoalBySort(SortType.ALL)
        goalAdapter = ArchivedGoalAdapter(::showKeepDeleteDialog)
        goalConcatAdapter = ConcatAdapter(headerAdapter, goalAdapter)

        initLayout()
        addListeners()
        collectData()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        goalAdapter.checkForVisibleDeleteButton()
        return super.dispatchTouchEvent(ev)
    }

    private fun initLayout() {
        binding.rvGoalList.apply {
            adapter = goalConcatAdapter
            itemAnimator = null
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun getFilteredGoalWithEatingType(eatingType: EatingType?) {
        when (eatingType) {
            null -> viewModel.fetchArchivedGoalBySort(SortType.ALL)
            EatingType.MORE -> viewModel.fetchArchivedGoalBySort(SortType.MORE)
            EatingType.LESS -> viewModel.fetchArchivedGoalBySort(SortType.LESS)
        }
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            moveToPrevious()
        }
        binding.btnMoreKeep.setOnClickListener {
            moveToHome()
        }
    }

    private fun collectData() {
        viewModel.archivedGoalFetchUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    goalAdapter.setGoalList(it.data.toMutableList())
                }
                is UiState.Error -> {} // TODO state에 따른 ui 업데이트 필요시 작성
                is UiState.Loading -> {}
                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.goalDeleteState.flowWithLifecycle(lifecycle).onEach { deleteState ->
            when (deleteState) {
                is UiState.Success -> {
                    goalAdapter.removeGoal(deleteState.data)
                    showToast(getString(R.string.goal_detail_success_goal_delete_toast_message))
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.allArchivedGoalCount.flowWithLifecycle(lifecycle).onEach { allAchievedGoalCount ->
            val homeGoalCount = intent.getIntExtra(ARG_HOME_GOAL_COUNT, -1)
            if (homeGoalCount == 0 && allAchievedGoalCount == 0) {
                binding.btnMoreKeep.visibility = View.VISIBLE
            } else {
                binding.btnMoreKeep.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun moveToMy() {
        if (viewModel.deletedGoalCount > 0)
            Intent(this, MyActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }.also {
                startActivity(it)
            }
        else
            finish()
    }

    private fun moveToPrevious() {
        if (isEnteredFromKeep) moveToHome()
        else moveToMy()
    }

    private fun showKeepDeleteDialog(goalId: Int) {
        GoalDeleteDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_GOAL_ID, goalId)
            }
        }.show(supportFragmentManager, "archivedGoalDeleteDialog")
    }

    companion object {
        const val ARG_IS_ENTERED_FROM_KEEP = "isEnteredFromKeep"
        const val ARG_GOAL_ID = "goalId"
        const val ARG_HOME_GOAL_COUNT = "homeGoalCount"
    }
}
