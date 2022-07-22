package com.dobrowins.extremelyinconvenientmessenger.data

import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteApi
import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteResponse

class OneTymeRemoteDummyRepository(
    private val api: CreateNoteApi? = null,
) : OneTymeRemoteRepository {

    override suspend fun createNote(note: String): CreateNoteResponse {
        return CreateNoteResponse(url = "url")
    }

    fun createNoteWithError(): CreateNoteResponse =
        CreateNoteResponse(url = "", error = "NOOOOOOOOO")
}