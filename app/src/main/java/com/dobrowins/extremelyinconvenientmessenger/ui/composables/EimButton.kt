package com.dobrowins.extremelyinconvenientmessenger.ui.composables

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.dobrowins.extremelyinconvenientmessenger.R

@Composable
fun EimButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.eim_red),
        ),
        onClick = onClick,
        modifier = modifier
            .wrapContentSize()
    ) {
        Text(
            text = text.uppercase(),
            color = colorResource(id = R.color.white),
        )
    }
}
