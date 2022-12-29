package org.keepgoeat.presentation.dummy

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityDummyBinding
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class DummyActivity : BindingActivity<ActivityDummyBinding>(R.layout.activity_dummy) {
    private val viewModel: DummyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
//        binding.lifecycleOwner = this // binding에서 LiveData를 사용할 경우 해당 코드 필요
    }

    private fun initLayout() {
        TODO("Not yet implemented")
    }

    private fun addListeners() {
        TODO("Not yet implemented")
    }

    private fun addObservers() {
        TODO("Not yet implemented")
    }
}