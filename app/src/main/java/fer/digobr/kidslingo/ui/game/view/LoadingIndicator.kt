package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize() then modifier
    ) {
        CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
