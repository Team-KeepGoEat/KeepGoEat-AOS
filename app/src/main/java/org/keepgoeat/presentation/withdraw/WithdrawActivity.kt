package org.keepgoeat.presentation.withdraw

import android.os.Bundle
import androidx.activity.viewModels
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityWithdrawBinding
import org.keepgoeat.util.binding.BindingActivity

class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val viewModel: WithdrawViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding.viewModel = viewModel
        binding.lifecycleOwner = this


    }
}