package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TypingGame(
    userAnswer: String?,
    onAnswerChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = userAnswer ?: "",
        onValueChange = {
            onAnswerChanged(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.DarkGray
        )
    )
}
