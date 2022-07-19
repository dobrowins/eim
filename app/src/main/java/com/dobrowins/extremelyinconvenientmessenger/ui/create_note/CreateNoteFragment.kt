package com.dobrowins.extremelyinconvenientmessenger.ui.create_note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import arrow.core.andThen
import com.dobrowins.extremelyinconvenientmessenger.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CreateNoteFragment : Fragment() {

    private val viewModel: CreateNoteViewModel by viewModels()

    private val exceptionHandler: (suspend () -> Unit) -> CoroutineExceptionHandler = { retry ->
        CoroutineExceptionHandler { _, t ->
            viewModel.onEvent(CreateNoteEvent.OnError(t, retry))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.collectAsState()
                val scope = rememberCoroutineScope()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                ) {

                    NotepadSurface(
                        note = state.value.note,
                        onValueChange = CreateNoteEvent::OnNoteChanged andThen viewModel::onEvent,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )

                    EimButton(
                        text = "Generate link",
                        onClick = {
                            if (state.value.note.isNotEmpty()) {
                                CreateNoteEvent.CreateNote.run(viewModel::onEvent)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 16.dp)
                    )

                    if (state.value.noteUrl.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            val url = state.value.noteUrl
                            putExtra(Intent.EXTRA_TEXT, url)
                        }
                        val ctx = LocalContext.current
                        Intent.createChooser(intent, "Share via").run(ctx::startActivity)
                        viewModel.onEvent(CreateNoteEvent.OnNoteCreated)
                    }

                    // handle error
                    val error = state.value.error
                    if (error.message.isNotEmpty()) {
                        val ctx = LocalContext.current
                        val text = String.format(resources.getString(R.string.error_formattable), error.message)
                        val retryButtonTitle = context.getString(R.string.snackbar_button_title_retry)
                        Snackbar
                            .make(ctx, this@apply, text, Snackbar.LENGTH_INDEFINITE)
                            .setAction(
                                retryButtonTitle, {
                                    val retryFunc = error.retryFunc
                                    scope.launch(exceptionHandler(retryFunc)) { retryFunc() }
                                }
                            )
                            .show()
                    }
                }
            }
        }
    }

}