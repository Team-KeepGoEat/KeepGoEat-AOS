package org.keepgoeat.util.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.toStateFlow(coroutineScope: CoroutineScope, initialValue: T) =
    stateIn(coroutineScope, SharingStarted.Eagerly, initialValue)
