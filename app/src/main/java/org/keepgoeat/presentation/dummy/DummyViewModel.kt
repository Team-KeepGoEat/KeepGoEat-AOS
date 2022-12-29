package org.keepgoeat.presentation.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.repository.DummyRepository
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(private val dummyRepository: DummyRepository) :
    ViewModel() {
    fun uploadDummy() {
        viewModelScope.launch {
            dummyRepository.uploadDummy("keepgoeat", "keepgoeat@gmail.com")
        }
    }
}