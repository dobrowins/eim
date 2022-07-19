package com.dobrowins.extremelyinconvenientmessenger.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface CreateNoteApi {

    @POST("/")
    suspend fun createNote(
        @Body body: RequestBody,
        @Query("mode") mode: String,
        @Query("cmd") cmd: String,
    ): CreateNoteResponse
}