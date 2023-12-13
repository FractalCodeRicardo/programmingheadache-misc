package com.thisisthetime.textfields

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thisisthetime.textfields.ui.theme.TextFieldsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextFieldsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextFields()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextFields() {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(50.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label TextField") },
            leadingIcon = {
                Icon(
                    Icons.Default.ExitToApp,
                    "ExitToApp",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.secondary
            ),
            visualTransformation = EmojiTransformation(),
            shape = CircleShape,
            supportingText = {Text("Supporting text")},
            placeholder ={Text("Placeholder text")}
        )

        Spacer(Modifier.height(50.dp))

        Column(modifier = Modifier.fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 20.dp)
            ) {
            Text("Label BasicTextField")
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.List,
                    "List",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(25.dp))
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.secondary),
                    visualTransformation = EmojiTransformation()

                )
            }
            Text("Supporting text")
        }


        Spacer(Modifier.height(50.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label OutlinedTextField") },
            leadingIcon = {
                Icon(
                    Icons.Default.MailOutline,
                    "MailOutline",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.secondary
            ),
            visualTransformation = EmojiTransformation(),
            shape = CircleShape,
            supportingText = {Text("Supporting text")},
            placeholder ={Text("Placeholder text")}
        )
    }
}



private class EmojiTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        var text = originalText

        if (originalText.isNotEmpty()) {
            text = "$originalText ( ͡° ͜ʖ ͡°)"
        }


        val offset = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return offset
                if (offset > originalText.length) {
                    return originalText.length
                }
                return offset
            }
        }
        return TransformedText(AnnotatedString(text), offset)
    }
}