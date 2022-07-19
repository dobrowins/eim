package com.dobrowins.extremelyinconvenientmessenger.ui.create_note

import com.dobrowins.extremelyinconvenientmessenger.EimError

data class CreateNoteState(
    val note: String = "",
    val noteUrl: String = "",
    val error: EimError = EimError(message = ""),
)

sealed class CreateNoteEvent {
    class OnNoteChanged(val noteText: String) : CreateNoteEvent()
    object CreateNote : CreateNoteEvent()
    object OnNoteCreated : CreateNoteEvent()
    class OnError(val throwable: Throwable, val retryFunc: suspend () -> Unit) : CreateNoteEvent()
}