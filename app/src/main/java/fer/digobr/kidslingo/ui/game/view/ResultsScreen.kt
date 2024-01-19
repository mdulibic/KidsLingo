package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.theme.AppOrange
import fer.digobr.kidslingo.ui.game.model.ResultsUiState

@Composable
fun ResultsScreen(
    uiState: ResultsUiState,
    onContinueClick: () -> Unit
) {
    Column(
        modifier = Modifier.background(AppGreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(id = R.string.results),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 32.sp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
            ) {
                Image(
                    modifier = Modifier.height(300.dp).padding(bottom = 32.dp),
                    painter = painterResource(id = R.drawable.owl_vector),
                    contentDescription = null
                )
                Text(
                    text = stringResource(
                        id = R.string.results_score,
                        uiState.score,
                        uiState.questionCount
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(top = 48.dp)
                )
            }
            Text(
                text = stringResource(id = uiState.messageRes),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,
            )
        }
        OutlinedButton(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = AppOrange),
            onClick = { onContinueClick() },
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_continue),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 18.sp,
            )
        }
    }
}
