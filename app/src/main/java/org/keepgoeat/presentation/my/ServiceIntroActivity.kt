package org.keepgoeat.presentation.my

import android.os.Bundle
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityServiceIntroBinding
import org.keepgoeat.util.binding.BindingActivity

class ServiceIntroActivity :
    BindingActivity<ActivityServiceIntroBinding>(R.layout.activity_service_intro) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.tvOpenSource.setOnClickListener {
            moveToOpenSourcePage()
        }
    }

    private fun moveToOpenSourcePage() {
        // TODO 오픈소스 페이지 연결하기
    }
}
