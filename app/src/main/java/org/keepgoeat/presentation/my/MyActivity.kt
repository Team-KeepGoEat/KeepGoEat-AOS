package org.keepgoeat.presentation.my

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint
import org.keepgoeat.BuildConfig
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityMyBinding
import org.keepgoeat.presentation.common.WebViewActivity
import org.keepgoeat.util.binding.BindingActivity
import timber.log.Timber

@AndroidEntryPoint
class MyActivity : BindingActivity<ActivityMyBinding>(R.layout.activity_my) {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.viewToolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.tvUserName.setOnClickListener {
            startActivity(Intent(this, AccountInfoActivity::class.java))
        }
        binding.tvAchievedGoal.setOnClickListener {
            moveToAchievedGoalDetail()
        }
        binding.tvAchievedGoalCount.setOnClickListener {
            moveToAchievedGoalDetail()
        }
        binding.ivAchievedGoalDetail.setOnClickListener {
            moveToAchievedGoalDetail()
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
            showReviewDialog()
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

    private fun moveToAchievedGoalDetail() {
        startActivity(Intent(this, AchievedGoalActivity::class.java))
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
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                manager.launchReviewFlow(this, task.result)
                    .addOnCompleteListener {
                        Timber.d("?????? ????????? ??????") // TODO ?????? ?????????
                    }.addOnFailureListener {
                        Timber.e(task.exception?.message)
                    }
            } else {
                Timber.e(task.exception?.message)
            }
        }
    }

    private fun moveToWebPage(link: String) {
        Intent(this, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.ARG_WEB_VIEW_LINK, link)
        }.also { startActivity(it) }
    }

    private fun collectData() {
        // TODO ????????? ?????? ?????? ????????????
    }

    companion object {
        private const val TERMS_LINK =
            "https://68space.notion.site/7d49b1a8912440cb9ec262392e5583e2"
        private const val POLICY_LINK =
            "https://68space.notion.site/9083a018baab42958103596378417c13"
    }
}
