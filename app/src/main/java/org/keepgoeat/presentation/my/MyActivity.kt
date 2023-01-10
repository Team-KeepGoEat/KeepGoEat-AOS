package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.presentation.home.HomeActivity
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class MyActivity : BindingActivity<ActivityMyBinding>(R.layout.activity_my) {
    private val viewModel: MyViewModel by viewModels()
    private val goalAdapter = MyGoalAdapter()
    private val headerAdapter = MyHeaderAdapter(::getFilteredGoalWithEatingType)
    private val goalConcatAdapter = ConcatAdapter(headerAdapter, goalAdapter)
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            moveToHome()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        addObservers()
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
        binding.ivBack.setOnClickListener {
            moveToHome()
        }
    }

    private fun addObservers() {
        viewModel.goalList.observe(this) { goals ->
            goalAdapter.submitList(goals.toMutableList())
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
