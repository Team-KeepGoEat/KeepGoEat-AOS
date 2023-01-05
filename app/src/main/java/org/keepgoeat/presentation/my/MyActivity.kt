package org.keepgoeat.presentation.my

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class MyActivity : BindingActivity<ActivityMyBinding>(R.layout.activity_my) {
    private val viewModel: MyViewModel by viewModels()
    private val goalAdapter = MyGoalAdapter()
    private val headerAdapter = MyHeaderAdapter(::getFilteredGoalWithEatingType)
    private val goalConcatAdapter = ConcatAdapter(headerAdapter, goalAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.rvGoalList.adapter = goalConcatAdapter
    }

    private fun getFilteredGoalWithEatingType(eatingType: EatingType?) {
        when (eatingType) {
            null -> goalAdapter.submitList(
                viewModel.goalList.value?.toMutableList()
            )
            EatingType.MORE -> goalAdapter.submitList(
                viewModel.goalList.value?.filter { it.eatingType == EatingType.MORE }
                    ?.toMutableList()
            )
            EatingType.LESS -> goalAdapter.submitList(
                viewModel.goalList.value?.filter { it.eatingType == EatingType.LESS }
                    ?.toMutableList()
            )
        }
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun addObservers() {
        viewModel.goalList.observe(this) { goals ->
            goalAdapter.submitList(goals.toMutableList())
        }
    }
}
