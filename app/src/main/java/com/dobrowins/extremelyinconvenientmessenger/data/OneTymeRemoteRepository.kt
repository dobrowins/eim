package com.dobrowins.extremelyinconvenientmessenger.data

import com.dobrowins.extremelyinconvenientmessenger.data.network.CreateNoteResponse

interface OneTymeRemoteRepository {

    suspend fun createNote(note: String): CreateNoteResponse
}

