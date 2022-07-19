package com.dobrowins.extremelyinconvenientmessenger.domain

import com.dobrowins.extremelyinconvenientmessenger.EimError

data class CreateNoteResult(
    val url: String,
    val error: EimError? = null,
)