package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.ui.game.GameViewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {
    val url by gameViewModel.url.collectAsState()
    GameLayout(url = url ?: "")
}

@Composable
fun GameLayout(
    url: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGreen)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
                //placeholder(R.drawable.placeholder) // Optional: Placeholder drawable while loading
            }
        )
        Image(
            painter = painter,
            contentDescription = "",
            modifier = modifier
                .height(48.dp),
        )
    }

}
