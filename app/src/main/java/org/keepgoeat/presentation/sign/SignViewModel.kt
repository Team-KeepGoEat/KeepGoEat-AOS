package org.keepgoeat.presentation.sign

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.keepgoeat.data.service.SignService
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val signService: SignService) : ViewModel() {
    fun login(loginListener: () -> Unit) {
//        signService.loginKakao(loginListener)
    }
}
