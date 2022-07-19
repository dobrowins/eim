package com.dobrowins.extremelyinconvenientmessenger.data.network

import com.google.gson.annotations.SerializedName

data class CreateNoteRequestData(val note: String) {

    companion object {

        const val mode: String = "ajax"
        const val cmd: String = "create_note"
    }
}

data class CreateNoteResponse(
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("url")
    val url: String? = null,
)
