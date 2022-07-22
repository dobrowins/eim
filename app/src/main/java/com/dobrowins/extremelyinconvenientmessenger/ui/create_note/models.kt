package com.dobrowins.extremelyinconvenientmessenger.ui.create_note

import com.dobrowins.extremelyinconvenientmessenger.common.EimError

data class CreateNoteState(
    val note: String = "",
    val noteUrl: String = "",
    val error: EimError? = null,
)

sealed class CreateNoteEvent {
    class OnNoteChanged(val noteText: String) : CreateNoteEvent()
    class CreateNote(val note: String) : CreateNoteEvent()
    object OnNoteCreated : CreateNoteEvent()
    class OnError(val throwable: Throwable) : CreateNoteEvent()
}