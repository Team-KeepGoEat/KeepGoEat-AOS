package org.keepgoeat.presentation.my

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.BuildConfig
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.presentation.base.screen.MixpanelActivity
import org.keepgoeat.presentation.common.WebViewActivity
import org.keepgoeat.presentation.my.archive.ArchivedGoalActivity
import org.keepgoeat.util.extension.showToast

@AndroidEntryPoint
class MyActivity : MixpanelActivity<ActivityMyBinding>(R.layout.activity_my, SCREEN_NAME) {
    override val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.fetchUserInfo()
        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.tvVersionName.text = BuildConfig.VERSION_NAME
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.tvUserName.setOnClickListener {
            Intent(this, AccountInfoActivity::class.java).apply {
                putExtra(ARG_USER_INFO, viewModel.userInfo.value)
            }.also {
                startActivity(it)
            }
        }
        binding.layoutArchivedGoal.setOnClickListener {
            moveToArchivedGoalDetail()
        }
        binding.tvContactUs.setOnClickListener {
            sendMail(
                getString(R.string.my_contact_us_mail_title),
                String.format(
                    getString(R.string.my_contact_us_mail_content),
                    Build.BRAND,
                    Build.DEVICE,
                    BuildConfig.VERSION_NAME,
                    Build.VERSION.SDK_INT,
                    Build.VERSION.RELEASE
                )
            )
        }
        binding.tvFeedback.setOnClickListener {
            moveToPlayStore()
        }
        binding.tvAboutService.setOnClickListener {
            startActivity(Intent(this, ServiceIntroActivity::class.java))
        }
        binding.tvTerms.setOnClickListener {
            moveToWebPage(TERMS_LINK)
        }
        binding.tvPolicy.setOnClickListener {
            moveToWebPage(POLICY_LINK)
        }
    }

    private fun moveToArchivedGoalDetail() {
        val homeGoalCount = intent.getIntExtra(ARG_HOME_GOAL_COUNT, -1)
        val intent = Intent(this, ArchivedGoalActivity::class.java)
        intent.putExtra(ARG_HOME_GOAL_COUNT, homeGoalCount)
        startActivity(intent)
    }

    private fun sendMail(title: String, content: String) {
        Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.keep_go_eat_mail)))
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, content)
        }.also { startActivity(it) }
    }

    private fun showReviewDialog() {
        val manager = ReviewManagerFactory.create(this)
        manager.requestReviewFlow().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                manager.launchReviewFlow(this, task.result)
                    .addOnCompleteListener {
                        showToast(getString(R.string.my_feedback_success_toast_message))
                    }
            } else {
                moveToPlayStore()
            }
        }
    }

    private fun moveToWebPage(link: String) {
        Intent(this, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.ARG_WEB_VIEW_LINK, link)
        }.also { startActivity(it) }
    }

    private fun moveToPlayStore() {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.play_store_detail_url) + packageName)
        }.also {
            startActivity(it)
        }
    }

    companion object {
        private const val TERMS_LINK =
            "https://68space.notion.site/7d49b1a8912440cb9ec262392e5583e2"
        private const val POLICY_LINK =
            "https://68space.notion.site/9083a018baab42958103596378417c13"
        const val ARG_HOME_GOAL_COUNT = "homeGoalCount"
        const val ARG_USER_INFO = "userInfo"
        private const val SCREEN_NAME = "mypage"
    }
}
