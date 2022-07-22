package com.dobrowins.extremelyinconvenientmessenger.common

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface ShowsSnackbar {

    fun ScaffoldState.showSnackbar(
        data: SnackbarData,
        scope: CoroutineScope,
        onResult: (SnackbarResult) -> Unit,
    ) {
        scope.launch {
            val snackBarResult = snackbarHostState.showSnackbar(
                message = data.message,
                actionLabel = data.buttonTitle,
                duration = SnackbarDuration.Indefinite,
            )
            onResult(snackBarResult)
        }
    }
}