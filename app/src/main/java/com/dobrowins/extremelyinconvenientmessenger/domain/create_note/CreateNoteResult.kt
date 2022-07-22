package com.dobrowins.extremelyinconvenientmessenger.domain.create_note

import com.dobrowins.extremelyinconvenientmessenger.common.EimError

data class CreateNoteResult(
    val url: String = "",
    val error: EimError? = null,
)