package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityAchievedGoalBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.binding.BindingActivity
import org.keepgoeat.util.extension.showToast

@AndroidEntryPoint
class AchievedGoalActivity :
    BindingActivity<ActivityAchievedGoalBinding>(R.layout.activity_achieved_goal) {
    private val viewModel: MyViewModel by viewModels()
    lateinit var goalAdapter: AchievedGoalAdapter
    private val headerAdapter = AchievedGoalHeaderAdapter(::getFilteredGoalWithEatingType)
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

        goalAdapter = AchievedGoalAdapter(::showKeepDeleteDialog)
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
            null -> viewModel.fetchAchievedGoalBySort(SortType.ALL)
            EatingType.MORE -> viewModel.fetchAchievedGoalBySort(SortType.MORE)
            EatingType.LESS -> viewModel.fetchAchievedGoalBySort(SortType.LESS)
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
        viewModel.achievedGoalUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    goalAdapter.setGoalList(it.data.toMutableList())
                }
                is UiState.Error -> {} // TODO state??? ?????? ui ???????????? ????????? ??????
                is UiState.Loading -> {}
                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.deleteState.flowWithLifecycle(lifecycle).onEach { deleteState ->
            when (deleteState) {
                is UiState.Success -> {
                    goalAdapter.removeGoal(deleteState.data)
                    showToast(getString(R.string.goal_detail_success_goal_delete_toast_message))
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun moveToPrevious() {
        if (isEnteredFromKeep) moveToHome()
        else finish()
    }

    private fun showKeepDeleteDialog(goalId: Int) {
        KeepDeleteDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_GOAL_ID, goalId)
            }
        }.show(supportFragmentManager, "KeepDeleteDialog")
    }

    companion object {
        const val ARG_IS_ENTERED_FROM_KEEP = "isEnteredFromKeep"
        const val ARG_GOAL_ID = "goalId"
    }
}
