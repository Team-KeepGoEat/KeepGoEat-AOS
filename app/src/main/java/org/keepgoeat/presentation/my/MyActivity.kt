package org.keepgoeat.presentation.my

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class MyActivity : BindingActivity<ActivityMyBinding>(R.layout.activity_my) {
    private val viewModel: MyViewModel by viewModels()
    private val adapter = MyGoalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        binding.rvGoalList.adapter = adapter
    }


    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun addObservers() {
        viewModel.goalList.observe(this) { goals ->
            adapter.submitList(goals.toMutableList())
        }
    }
}
