package fer.digobr.kidslingo.ui.settings.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.GameLevel
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.theme.AppOrange
import fer.digobr.kidslingo.ui.settings.SettingsViewModel
import fer.digobr.kidslingo.ui.settings.model.SettingsUiState

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel
) {
    val uiState by settingsViewModel.uiState.collectAsState()
    SettingsLayout(
        uiState = uiState,
        onLanguageChanged = { settingsViewModel.onLanguageChanged(it) },
        onCategoryChanged = { settingsViewModel.onCategoryChanged(it) },
        onLevelChanged = { settingsViewModel.onLevelChanged(it) },
    )
}

@Composable
private fun SettingsLayout(
    uiState: SettingsUiState,
    onLanguageChanged: (GameLanguage) -> Unit,
    onCategoryChanged: (GameCategory) -> Unit,
    onLevelChanged: (GameLevel) -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.language),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LanguageSelection(
                selectedLanguage = uiState.selectedLanguage,
                onLanguageChanged = onLanguageChanged,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.category),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            CategorySelection(
                selectedCategory = uiState.selectedCategory,
                onCategoryChanged = onCategoryChanged,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.level),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LevelSelection(
                selectedLevel = uiState.selectedLevel,
                onLevelChanged = onLevelChanged,
            )
        }
        Image(
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.ic_hedgehog),
            contentDescription = null
        )
    }
}

@Composable
private fun LanguageSelection(
    selectedLanguage: GameLanguage,
    onLanguageChanged: (GameLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
    ) {
        GameLanguage.values().forEach { language ->
            SelectionButton(
                isSelected = language == selectedLanguage,
                selectionRes = language.titleRes,
                onClick = {
                    onLanguageChanged(language)
                }
            )
        }
    }
}

@Composable
private fun CategorySelection(
    selectedCategory: GameCategory,
    onCategoryChanged: (GameCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
    ) {
        GameCategory.values().forEach { category ->
            SelectionButton(
                isSelected = category == selectedCategory,
                selectionRes = category.titleRes,
                onClick = {
                    onCategoryChanged(category)
                }
            )
        }
    }
}

@Composable
private fun LevelSelection(
    selectedLevel: GameLevel,
    onLevelChanged: (GameLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
    ) {
        GameLevel.values().forEach { level ->
            SelectionButton(
                isSelected = level == selectedLevel,
                selectionRes = level.titleRes,
                onClick = {
                    onLevelChanged(level)
                }
            )
        }
    }
}

@Composable
private fun SelectionButton(
    isSelected: Boolean,
    selectionRes: Int,
    onClick: () -> Unit
) {
    val labelTextColor = Color.White
    val btnBgColor = if (isSelected) {
        AppOrange
    } else {
        AppGreen
    }

    val borderStroke = if (isSelected) {
        BorderStroke(2.dp, AppOrange)
    } else {
        BorderStroke(2.dp, Color.White)
    }
    OutlinedButton(
        border = borderStroke,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = btnBgColor),
        onClick = onClick,
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = stringResource(id = selectionRes),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = labelTextColor
        )
    }
}



