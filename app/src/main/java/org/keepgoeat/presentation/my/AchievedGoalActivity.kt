package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
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
class AchievedGoalActivity : BindingActivity<ActivityAchievedGoalBinding>(R.layout.activity_achieved_goal){
    private val viewModel: MyViewModel by viewModels()
    lateinit var goalAdapter : AchievedGoalAdapter
    private val headerAdapter = AchievedGoalHeaderAdapter(::getFilteredGoalWithEatingType)
    lateinit var goalConcatAdapter : ConcatAdapter
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

        goalAdapter = AchievedGoalAdapter(this, viewModel)
        goalConcatAdapter = ConcatAdapter(headerAdapter, goalAdapter)
        isEnteredFromKeep = intent.getBooleanExtra(ARG_IS_ENTERED_FROM_KEEP, false)

        initLayout()
        addListeners()
        collectData()
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        for (i in 1..goalAdapter.itemCount) {
            val deleteBtn =  binding.rvGoalList.findViewHolderForAdapterPosition(i)?.let {
                it.itemView.findViewById<Button>(R.id.btn_achieved_goal_delete)
            }
            adjustVisibility(deleteBtn)
        }
        return super.onTouchEvent(ev)
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
            // TODO 목표 추가하기 위한 뷰로 연결
        }
    }

    private fun collectData() {
        viewModel.achievedGoalUiState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    goalAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {} // TODO state에 따른 ui 업데이트 필요시 작성
                is UiState.Loading -> {}
                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.deleteState.flowWithLifecycle(lifecycle).onEach { deleteState ->
            when (deleteState) {
                is UiState.Success -> {
                    showToast(getString(R.string.goal_detail_success_goal_delete_toast_message))
                    startActivity(Intent(this, AchievedGoalActivity::class.java))
                    finish()
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

    private fun adjustVisibility(view: View?){
        view?.let {
            if(it.visibility == View.VISIBLE) {
                it.visibility = View.GONE
            }
        }
    }

    companion object {
        const val ARG_IS_ENTERED_FROM_KEEP = "isEnteredFromKeep"
    }
}
