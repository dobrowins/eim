package com.dobrowins.extremelyinconvenientmessenger.ui.create_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import arrow.core.andThen
import com.dobrowins.extremelyinconvenientmessenger.R
import com.dobrowins.extremelyinconvenientmessenger.common.ShareFragment
import com.dobrowins.extremelyinconvenientmessenger.common.ShowsSnackbar
import com.dobrowins.extremelyinconvenientmessenger.common.SnackbarData
import com.dobrowins.extremelyinconvenientmessenger.domain.create_note.CreateNoteException
import com.dobrowins.extremelyinconvenientmessenger.ui.composables.EimButton
import com.dobrowins.extremelyinconvenientmessenger.ui.composables.NotepadSurface

class CreateNoteFragment : Fragment(), ShareFragment, ShowsSnackbar {

    private val viewModel: CreateNoteViewModel by viewModels()
    private val createNote: (String) -> Unit get() = CreateNoteEvent::CreateNote andThen viewModel::onEvent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val state = viewModel.state.collectAsState()
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier,
                    scaffoldState = scaffoldState,
                ) {

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
                                .wrapContentHeight()
                        )

                        EimButton(
                            text = stringResource(R.string.title_generate_link),
                            onClick = {
                                state.value.note.run(createNote)
                            },
                            modifier = Modifier
                                .align(Alignment.End)
                                .wrapContentHeight()
                                .padding(end = 16.dp, bottom = 16.dp)
                        )

                        // show share.via dialog
                        if (state.value.noteUrl.isNotEmpty()) {
                            context
                                .showShareOptions(url = state.value.noteUrl)
                                .also { viewModel.onEvent(CreateNoteEvent.OnNoteCreated) }
                        }

                        // show snackbar with retry function
                        val error = state.value.error
                        if (error?.message?.isNotEmpty() == true) {
                            scaffoldState.showSnackbar(
                                data = SnackbarData(
                                    message = stringResource(id = R.string.error_formattable, formatArgs = arrayOf(error.message)),
                                    buttonTitle = stringResource(R.string.snackbar_button_title_retry),
                                ),
                                scope = scope,
                                onResult = { snackbarResult ->
                                    when (snackbarResult) {
                                        SnackbarResult.ActionPerformed -> {
                                            when (error.exception) {
                                                is CreateNoteException -> state.value.note.run(createNote)
                                                else -> Unit
                                            }
                                        }
                                        else -> Unit
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
