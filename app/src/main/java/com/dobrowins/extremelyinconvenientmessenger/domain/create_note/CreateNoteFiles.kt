package com.dobrowins.extremelyinconvenientmessenger.domain.create_note

import com.dobrowins.extremelyinconvenientmessenger.common.EimError
import com.dobrowins.extremelyinconvenientmessenger.data.OneTymeRemoteRepository
import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteResponse

class CreateNote constructor(
    private val repository: OneTymeRemoteRepository,
) {

    suspend fun from(note: String): CreateNoteResult =
        repository.createNote(note).mapToDomain()

    private fun CreateNoteResponse.mapToDomain(): CreateNoteResult {
        return CreateNoteResult(
            error = this.error
                ?.takeIf(String::isNotEmpty)
                ?.let { EimError(message = it, exception = CreateNoteException()) },
            url = this.url.orEmpty(),
        )
    }
}

class CreateNoteException : RuntimeException()
