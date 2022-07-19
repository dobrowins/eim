package com.dobrowins.extremelyinconvenientmessenger.ui.create_note

import com.dobrowins.extremelyinconvenientmessenger.DependenciesGraph
import com.dobrowins.extremelyinconvenientmessenger.EimError
import com.dobrowins.extremelyinconvenientmessenger.Handles
import com.dobrowins.extremelyinconvenientmessenger.domain.CreateNote
import com.dobrowins.extremelyinconvenientmessenger.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateNoteViewModel : BaseViewModel<CreateNoteState>(), Handles<CreateNoteEvent> {

    private val createNote: CreateNote = DependenciesGraph.Domain.createNote

    private val _state: MutableStateFlow<CreateNoteState> = MutableStateFlow(CreateNoteState())
    override val state = _state as StateFlow<CreateNoteState>

    override val handleError: (EimError) -> Unit = { eimError ->
        _state.value.copy(error = eimError)
            .run { _state.value = this }
    }

    override fun onEvent(event: CreateNoteEvent) {
        _state.value = _state.value.copy(error = EimError())
        when (event) {
            is CreateNoteEvent.OnNoteChanged -> {
                _state.value = _state.value.copy(note = event.noteText)
            }
            is CreateNoteEvent.CreateNote -> {
                safeLaunch {
                    val createdNote = createNote.from(note = state.value.note)
                    if (createdNote.error != null) {
                        val eimError = EimError(
                            message = createdNote.error.message,
                            retryFunc = { onEvent(CreateNoteEvent.CreateNote) },
                        )
                        val stateWithError = _state.value.copy(error = eimError)
                        _state.value = stateWithError
                        return@safeLaunch
                    }
                    val updatedState = _state.value.copy(noteUrl = "https://1ty.me/" + createdNote.url)
                    _state.value = updatedState
                }
            }
            is CreateNoteEvent.OnNoteCreated -> {
                val updatedState = CreateNoteState(note = _state.value.note)
                _state.value = updatedState
            }
            is CreateNoteEvent.OnError -> {
                val eimError = EimError(
                    message = event.throwable.message.orEmpty(),
                    retryFunc = event.retryFunc,
                )
                val stateWithError = _state.value.copy(error = eimError)
                _state.value = stateWithError
            }
        }
    }
}