package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.ui.game.GameViewModel
import fer.digobr.kidslingo.ui.home.HomeViewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGreen)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}
