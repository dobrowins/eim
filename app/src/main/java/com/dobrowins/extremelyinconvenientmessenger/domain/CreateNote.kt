package com.dobrowins.extremelyinconvenientmessenger.domain

import com.dobrowins.extremelyinconvenientmessenger.EimError
import com.dobrowins.extremelyinconvenientmessenger.data.OneTymeRemoteRepository
import com.dobrowins.extremelyinconvenientmessenger.data.network.CreateNoteResponse

class CreateNote constructor(
    private val repository: OneTymeRemoteRepository,
) {

    suspend fun from(note: String): CreateNoteResult {
        return repository.createNote(note).mapToDomain()
    }

    private fun CreateNoteResponse.mapToDomain(): CreateNoteResult {
        return CreateNoteResult(
            error = this.error
                ?.takeIf(String::isNotEmpty)
                ?.let { EimError(message = it) },
            url = this.url.orEmpty(),
        )
    }
}