package com.suraj854.myapplication.presentation.quiz.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.suraj854.myapplication.R
import com.suraj854.myapplication.presentation.theme.PrimaryColor

@Composable
fun QuizOptionItem(isEnable: Boolean, name: String, onChecked: () -> Unit = {}) {

    var check = isEnable
    var selectedColor = remember {
        mutableStateOf(Color.Transparent)
    }

    Button(
        onClick = {
            selectedColor.value = Color.Blue
            onChecked()
        },
        colors = ButtonDefaults.buttonColors(if (!check) Color.White else PrimaryColor.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (!check) Color.LightGray else Color(0xff2567E8),

            ),
        modifier = Modifier

            .fillMaxWidth().padding(vertical = 8.dp)


    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = 8.dp, vertical = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = if (!check) R.drawable.ic_unchecked else R.drawable.ic_check),
                    contentDescription = "",
                )

            }
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(0.8f)) {
                Text(
                    text = name,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

        }

    }
}