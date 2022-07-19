package com.dobrowins.extremelyinconvenientmessenger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobrowins.extremelyinconvenientmessenger.EimError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State> : ViewModel() {

    abstract val state: StateFlow<State>

    abstract val handleError: (EimError) -> Unit

    private val errorHandler: (suspend () -> Unit) -> CoroutineExceptionHandler = { retry ->
        CoroutineExceptionHandler { ctx, throwable ->
            throwable.message
                ?.let { EimError(message = it, retryFunc = { retry() }) }
                ?.run(handleError)
        }
    }

    fun safeLaunch(f: suspend () -> Unit) {
        viewModelScope.launch(errorHandler(f)) { f.invoke() }
    }

}