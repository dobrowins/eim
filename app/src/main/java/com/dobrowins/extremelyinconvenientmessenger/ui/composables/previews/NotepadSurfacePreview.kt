package com.dobrowins.extremelyinconvenientmessenger.ui.composables.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dobrowins.extremelyinconvenientmessenger.ui.composables.NotepadSurface

@Preview
@Composable
fun NotepadSurfacePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        NotepadSurface(note = "Hello, world!", onValueChange = {})
    }
}