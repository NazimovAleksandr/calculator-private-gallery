package com.next.level.solutions.calculator.fb.mp.ui.composable.text.field


import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
  initValue: String,
  placeholder: StringResource,
  modifier: Modifier = Modifier,
  textStyle: TextStyle = TextStyleFactory.FS16.w600(),
  singleLine: Boolean = false,
  onValueChange: (String) -> Unit,
) {
  var value by remember(key1 = initValue) { mutableStateOf(initValue) }
  var focusedValue by remember { mutableStateOf(false) }

  BasicTextField(
    value = value,
    singleLine = singleLine,
    textStyle = textStyle,
    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
    keyboardOptions = KeyboardOptions(
      capitalization = KeyboardCapitalization.Sentences,
    ),
    onValueChange = {
      value = it
      onValueChange(it)
    },
    modifier = modifier
      .border(
        width = 2.dp,
        color = when (focusedValue) {
          true -> MaterialTheme.colorScheme.primary
          else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        },
        shape = MaterialTheme.shapes.large,
      )
      .clip(shape = MaterialTheme.shapes.large)
      .onFocusChanged { focusedValue = it.isFocused }
  ) { innerTextField ->
    TextFieldDefaults.DecorationBox(
      value = value,
      visualTransformation = VisualTransformation.None,
      innerTextField = innerTextField,
      placeholder = {
        Text(
          text = stringResource(resource = placeholder),
          color = textStyle.color.copy(alpha = 0.3f),
          style = textStyle,
          modifier = Modifier.fillMaxWidth()
        )
      },
      singleLine = false,
      enabled = true,
      interactionSource = remember { MutableInteractionSource() },
      contentPadding = PaddingValues(vertical = 22.dp, horizontal = 24.dp),
      colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
      ),
    )
  }
}