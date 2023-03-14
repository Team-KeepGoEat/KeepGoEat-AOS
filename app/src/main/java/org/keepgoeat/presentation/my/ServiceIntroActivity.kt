package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Bundle
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityServiceIntroBinding
import org.keepgoeat.presentation.common.WebViewActivity
import org.keepgoeat.presentation.common.WebViewActivity.Companion.ARG_WEB_VIEW_LINK
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
        Intent(this, WebViewActivity::class.java).apply {
            putExtra(ARG_WEB_VIEW_LINK, OPEN_SOURCE_LINK)
        }.also { startActivity(it) }
    }

    companion object {
        private const val OPEN_SOURCE_LINK =
            "https://68space.notion.site/AOS-9d8b169f9108434baca652a9c1dd7c81"
    }
}
