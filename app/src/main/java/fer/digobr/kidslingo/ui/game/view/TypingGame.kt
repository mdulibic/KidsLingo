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
    modifier: Modifier = Modifier,
    onAnswerChanged: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newText ->
            text = newText
            onAnswerChanged(text)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.DarkGray
        )
    )
}
