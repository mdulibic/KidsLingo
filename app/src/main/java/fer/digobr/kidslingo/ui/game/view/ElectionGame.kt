package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.theme.AppCorrectGreen
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.theme.AppWrongRed

@Composable
fun ElectionGame(
    gameItem: GameItem.Election,
    isSolutionPreview: Boolean,
    onChoiceSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedChoice by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        gameItem.wordMap.keys.forEach { choice ->
            ChoiceButton(
                isSelected = choice == selectedChoice,
                isSolution = if (isSolutionPreview) choice == gameItem.word else null,
                choice = choice,
                onClick = {
                    selectedChoice = choice
                    onChoiceSelect(choice)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ChoiceButton(
    isSolution: Boolean?,
    isSelected: Boolean,
    choice: String,
    onClick: (String) -> Unit
) {
    val isEnabled = isSolution == null
    val labelTextColor = Color.White
    val btnBgColor = if (isSelected) {
        isSolution?.let {
            if (isSolution) {
                AppCorrectGreen
            } else {
                AppWrongRed
            }
        } ?: run {
            AppCorrectGreen
        }
    } else {
        isSolution?.let {
            if (isSolution) {
                AppCorrectGreen
            } else {
                AppGreen
            }
        } ?: run {
            AppGreen
        }
    }
    val borderStroke = if (isSelected) {
        BorderStroke(1.dp, Color.White)
    } else {
        BorderStroke(1.dp, Color.White)
    }
    OutlinedButton(
        border = borderStroke,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = btnBgColor),
        onClick = { if (isEnabled) onClick(choice) },
        contentPadding = PaddingValues(10.dp),
        enabled = isEnabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = choice,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = labelTextColor
        )
    }
}



