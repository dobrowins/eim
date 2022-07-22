package com.dobrowins.extremelyinconvenientmessenger.domain.create_note

import com.dobrowins.extremelyinconvenientmessenger.data.OneTymeRemoteDummyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class CreateNoteTest {

    private lateinit var createNote: CreateNote

    @Before
    fun setUp() {
        createNote = CreateNote(OneTymeRemoteDummyRepository())
    }

    @Test
    fun createNote() {
        runBlocking {
            val result = createNote.from("Hello, world!")
            assert(result.error == null)
            assert(result.url.isNotEmpty())
        }
    }
}