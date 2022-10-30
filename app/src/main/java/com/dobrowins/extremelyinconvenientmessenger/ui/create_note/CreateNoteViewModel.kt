package com.dobrowins.extremelyinconvenientmessenger.ui.create_note

import com.dobrowins.extremelyinconvenientmessenger.BuildConfig
import com.dobrowins.extremelyinconvenientmessenger.DependenciesGraph
import com.dobrowins.extremelyinconvenientmessenger.common.EimError
import com.dobrowins.extremelyinconvenientmessenger.common.Handles
import com.dobrowins.extremelyinconvenientmessenger.domain.create_note.CreateNote
import com.dobrowins.extremelyinconvenientmessenger.domain.create_note.CreateNoteException
import com.dobrowins.extremelyinconvenientmessenger.ui.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateNoteViewModel constructor(
    private val createNote: CreateNote = DependenciesGraph.Domain.createNote,
) : BaseViewModel<CreateNoteState>(), Handles<CreateNoteEvent> {

    private val _state: MutableStateFlow<CreateNoteState> = MutableStateFlow(CreateNoteState())
    override val state = _state as StateFlow<CreateNoteState>

    override val handleError: (EimError) -> Unit = { eimError ->
        _state.value.copy(error = eimError)
            .run { _state.value = this }
    }

    private var createNoteJob: Job? = null

    override fun onEvent(event: CreateNoteEvent) {
        _state.value = _state.value.copy(error = null)
        when (event) {
            is CreateNoteEvent.OnNoteChanged -> {
                _state.value = _state.value.copy(note = event.noteText)
            }
            is CreateNoteEvent.CreateNote -> {
                if (event.note.isEmpty()) return
                createNoteJob?.cancel()
                createNoteJob = safeLaunch(CreateNoteException()) {
                    val createdNote = createNote.from(note = event.note)
                    val error = createdNote.error
                    if (error != null) {
                        val stateWithError = _state.value.copy(error = error)
                        _state.value = stateWithError
                        return@safeLaunch
                    }
                    val updatedState =
                        _state.value.copy(noteUrl = BuildConfig.ENDPOINT + createdNote.url)
                    _state.value = updatedState
                }
            }
            is CreateNoteEvent.OnNoteCreated -> {
                val updatedState = CreateNoteState(note = _state.value.note)
                _state.value = updatedState
            }
            is CreateNoteEvent.OnError -> {
                val eimError = EimError(message = event.throwable.message.orEmpty())
                val stateWithError = _state.value.copy(error = eimError)
                _state.value = stateWithError
            }
        }
    }
}