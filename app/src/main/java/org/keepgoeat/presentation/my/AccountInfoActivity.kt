package org.keepgoeat.presentation.my

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityAccountInfoBinding
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class AccountInfoActivity : BindingActivity<ActivityAccountInfoBinding>(R.layout.activity_account_info) {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
    }
}
