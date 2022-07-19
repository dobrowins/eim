package com.dobrowins.extremelyinconvenientmessenger.data

import com.dobrowins.extremelyinconvenientmessenger.data.network.CreateNoteResponse

class OneTymeRemoteDummyRepository: OneTymeRemoteRepository {

    override suspend fun createNote(note: String): CreateNoteResponse =
        CreateNoteResponse(url = "", error = "NOOOOOOOOO")
}