package com.dobrowins.extremelyinconvenientmessenger.data

import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteApi
import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteRequestData
import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteResponse
import okhttp3.MultipartBody

class OneTymeRemoteRepositoryImpl constructor(
    private val api: CreateNoteApi,
) : OneTymeRemoteRepository {

    override suspend fun createNote(note: String): CreateNoteResponse {
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("note", note)
            .addFormDataPart("expires_on", "undefined")
            .build()

        return api.createNote(
            body = body,
            cmd = CreateNoteRequestData.cmd,
            mode = CreateNoteRequestData.mode,
        )
    }

}