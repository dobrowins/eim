package com.dobrowins.extremelyinconvenientmessenger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobrowins.extremelyinconvenientmessenger.common.EimError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<State> : ViewModel() {

    abstract val state: StateFlow<State>

    abstract val handleError: (EimError) -> Unit

    private val onError: (RuntimeException?) -> CoroutineExceptionHandler = { runtimeException ->
        CoroutineExceptionHandler { _, throwable ->
            throwable.message
                ?.let { EimError(message = it, exception = runtimeException) }
                ?.run(handleError)
        }
    }

    fun safeLaunch(
        exception: RuntimeException? = null,
        f: suspend () -> Unit,
    ) {
        viewModelScope.launch(onError(exception)) { withContext(viewModelScope.coroutineContext) { f() } }
    }
}
