package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityServiceIntroBinding
import org.keepgoeat.presentation.common.WebViewActivity
import org.keepgoeat.presentation.common.WebViewActivity.Companion.ARG_WEB_VIEW_LINK
import org.keepgoeat.util.binding.BindingActivity

@AndroidEntryPoint
class ServiceIntroActivity :
    BindingActivity<ActivityServiceIntroBinding>(R.layout.activity_service_intro) {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRecodingScreenTime()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRecodingScreenTime(SCREEN_NAME)
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
        Intent(this, WebViewActivity::class.java).apply {
            putExtra(ARG_WEB_VIEW_LINK, OPEN_SOURCE_LINK)
        }.also { startActivity(it) }
    }

    companion object {
        private const val OPEN_SOURCE_LINK =
            "https://68space.notion.site/cd16c9399dfe4c7a867deb59851282e3"
        private const val SCREEN_NAME = "service intro"
    }
}
