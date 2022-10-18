package com.vron.cstv.common.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vron.cstv.common.ui.compose.theme.CSTVTheme

@Composable
fun LoadingBar(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview
@Composable
fun LoadingBarPreview() {
    CSTVTheme {
        Surface {
            LoadingBar()
        }
    }
}