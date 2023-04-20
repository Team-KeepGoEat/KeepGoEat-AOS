package org.keepgoeat.util.mixpanel

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import org.keepgoeat.BuildConfig
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.presentation.model.MixPanelEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MixpanelProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localStorage: KGEDataSource,
) {
    private var instance: MixpanelAPI =
        MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_PROJECT_TOKEN, true)

    fun setUser() {
        instance.identify(localStorage.userEmail)
        instance.people.set(getUserProperties())
    }

    private fun getUserProperties() = JSONObject().apply {
        put(PROPERTY_NICKNAME, localStorage.userName)
        put(PROPERTY_EMAIL, localStorage.userEmail)
    }

    /** 믹스패널에 이벤트를 전송하는 함수. 유저 프로퍼티를 함께 전송해야하는 경우, isRequiredUserProps를 false로 설정 필요 */
    fun sendEvent(event: MixPanelEvent, isRequiredUserProps: Boolean = true) {
        val props = if (isRequiredUserProps) getUserProperties() else JSONObject()

        event.params?.let {
            for ((key, value) in it)
                props.put(key, value)
        }

        instance.track(event.name, props)
    }

    companion object {
        private const val PROPERTY_NICKNAME = "\$name"
        private const val PROPERTY_EMAIL = "\$email"
    }
}
