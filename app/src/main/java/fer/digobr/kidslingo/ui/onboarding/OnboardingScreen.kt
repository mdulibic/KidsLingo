package fer.digobr.kidslingo.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.theme.AppGrey
import fer.digobr.kidslingo.theme.AppOrange

@Composable
fun OnboardingScreen(
    onStartClick: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().background(AppGrey),
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.height(120.dp).fillMaxWidth().padding(bottom = 32.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.enter_name),
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp),
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.DarkGray
                )
            )
            Column(
                modifier = Modifier.padding(48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(id = R.drawable.bear_giraffe),
                    contentDescription = null
                )
            }
        }
        OutlinedButton(
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = AppOrange),
            onClick = { onStartClick(text) },
            enabled = text.isNotEmpty(),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.start),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 18.sp,
            )
        }
    }
}
