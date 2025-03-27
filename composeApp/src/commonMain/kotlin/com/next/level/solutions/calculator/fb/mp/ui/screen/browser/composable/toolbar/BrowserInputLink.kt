package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.search_or_type_url
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Close
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Reload
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.BrowserInputLinkActions
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
@Suppress("NOTHING_TO_INLINE") // for fix recompositions
inline fun BrowserInputLink(
  title: String?,
  url: String?,
  isEditLink: Int,
  linkEditor: Boolean,
  modifier: Modifier = Modifier,
  colors: TextFieldColors = getDefColor(),
  noinline action: (BrowserInputLinkActions) -> Unit,
) {
  InputLink(
    title = title,
    url = url,
    isEditLink = isEditLink,
    linkEditor = linkEditor,
    modifier = modifier,
    colors = colors,
    action = action,
  )
}

@Composable
@Suppress("NOTHING_TO_INLINE") // for fix recompositions
inline fun InputLink(
  title: String?,
  url: String?,
  modifier: Modifier = Modifier,
  colors: TextFieldColors = getDefColor(),
  isEditLink: Int = 0,
  linkEditor: Boolean = false,
  noinline action: (BrowserInputLinkActions) -> Unit = {},
) {
  val focusManager = LocalFocusManager.current

  val focusRequester = remember { FocusRequester() }
  var lastEditLink by remember { mutableIntStateOf(isEditLink) }
  var isFocused: Boolean by remember { mutableStateOf(false) }

  val linkTextFieldValue = remember {
    mutableStateOf(TextFieldValue(title ?: url ?: ""))
  }

  val onFocusChanged: (FocusState) -> Unit = {
    isFocused = it.isFocused
    action(BrowserInputLinkActions.OpenLinkEditor(isFocused))
  }

  val onGo: KeyboardActionScope.() -> Unit = {
    if (linkTextFieldValue.value.text.isNotEmpty()) {
      action(BrowserInputLinkActions.ClickLoad(linkTextFieldValue.value.text))
    }

    focusManager.clearFocus()
  }

  val onValueChange: (TextFieldValue) -> Unit = { value ->
    linkTextFieldValue.value = value
    action(BrowserInputLinkActions.Search(value.text))
  }

  Row(
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .clip(shape = MaterialTheme.shapes.large)
      .border(
        width = 2.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.large
      )
      .background(
        color = when {
          isFocused -> colors.focusedContainerColor
          else -> colors.unfocusedContainerColor
        }
      )
  ) {
    Box(
      contentAlignment = Alignment.CenterStart,
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      if (!isFocused) {
        Text(
          text = linkTextFieldValue.value.text,
          style = TextStyleFactory.FS16.w600(),
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
          modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = null, indication = null, onClick = { isFocused = true })
            .padding(start = 17.dp, bottom = 0.5.dp)
        )
      } else {
        Text(
          text = linkTextFieldValue.value.text,
          style = TextStyleFactory.FS16.w600(),
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
          modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = null, indication = null, onClick = { isFocused = true })
            .padding(start = 17.dp, bottom = 0.5.dp)
        )

        LinkTextField(
          value = linkTextFieldValue.value,
          textStyle = TextStyleFactory.FS16.w600(),
          colors = colors,
          shape = RoundedCornerShape(size = 0.dp),
          contentPadding = PaddingValues(all = 0.dp),
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
          keyboardActions = KeyboardActions(onGo = onGo),
          onValueChange = onValueChange,
          placeholder = {
            Placeholder()
          },
          modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester = focusRequester)
            .onFocusChanged(onFocusChanged = onFocusChanged)
            .graphicsLayer { alpha = if (isFocused) 1f else 0f }
            .padding(start = 17.dp, bottom = 0.5.dp)
            .padding(vertical = 4.dp)
        )

        LaunchedEffect(
          key1 = Unit,
          block = { focusRequester.requestFocus() },
        )
      }
    }

    TrailingIcon(
      linkEditor = linkEditor && linkTextFieldValue.value.text.isNotEmpty(),
      reload = linkTextFieldValue.value.text.isNotEmpty(),
      actionClose = {
        linkTextFieldValue.value = TextFieldValue("")
        action(BrowserInputLinkActions.Search(""))
      },
      actionReload = {
        action(BrowserInputLinkActions.ClickReload)
      },
    )
  }

  LaunchedEffect(
    key1 = url,
    key2 = title,
  ) {
    linkTextFieldValue.value = TextFieldValue(title ?: url ?: "")
  }

  LaunchedEffect(
    key1 = linkEditor,
    key2 = isEditLink,
  ) {
    linkTextFieldValue.value = when {
      isEditLink > lastEditLink -> TextFieldValue(url ?: "", TextRange(url?.length ?: 0))
      linkEditor -> TextFieldValue(url ?: "", TextRange(url?.length ?: 0))
      else -> TextFieldValue(title ?: url ?: "")
    }

    lastEditLink = isEditLink

    if (isFocused) action(BrowserInputLinkActions.Search(linkTextFieldValue.value.text))
  }

  LifecycleEventListener(
    onStop = focusManager::clearFocus,
  )
}

@Composable
fun Placeholder() {
  Text(
    text = stringResource(Res.string.search_or_type_url),
    style = TextStyleFactory.FS15.w400(
      color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    modifier = Modifier
  )
}

@Composable
fun TrailingIcon(
  linkEditor: Boolean,
  reload: Boolean,
  actionClose: () -> Unit,
  actionReload: () -> Unit,
) {
  val modifier = remember {
    Modifier
      .padding(start = 2.dp, end = 9.dp)
      .padding(vertical = 6.dp)
      .size(size = 40.dp)
      .clip(shape = CircleShape)
  }

  when {
    linkEditor -> Image(
      vector = MagicIcons.All.Close,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = modifier
        .clickable(onClick = actionClose)
        .padding(all = 12.dp)
    )

    reload -> Image(
      vector = MagicIcons.All.Reload,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = modifier
        .clickable(onClick = actionReload)
        .padding(all = 8.dp)
    )

    else -> Image(
      vector = MagicIcons.All.Close,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier
        .padding(end = 15.dp)
        .height(height = 52.dp)
        .width(width = 2.dp)
        .alpha(alpha = 0f)
    )
  }
}

@Composable
fun getDefColor(): TextFieldColors = TextFieldDefaults.colors(
  focusedContainerColor = MaterialTheme.colorScheme.background,
  focusedIndicatorColor = Color.Transparent,
  focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,

  unfocusedContainerColor = MaterialTheme.colorScheme.background,
  unfocusedIndicatorColor = Color.Transparent,
  unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),

  errorContainerColor = MaterialTheme.colorScheme.errorContainer,
  errorIndicatorColor = Color.Transparent,
  errorLeadingIconColor = MaterialTheme.colorScheme.onError,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkTextField(
  value: TextFieldValue,
  onValueChange: (TextFieldValue) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  onClickIfReadOnly: (() -> Unit)? = null,
  textStyle: TextStyle = TextStyleFactory.FS17.w400(color = Color.Unspecified),
  label: @Composable (() -> Unit)? = null,
  placeholder: @Composable (() -> Unit)? = null,
  leadingIcon: @Composable (() -> Unit)? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  prefix: @Composable (() -> Unit)? = null,
  suffix: @Composable (() -> Unit)? = null,
  supportingText: @Composable (() -> Unit)? = null,
  isError: Boolean = false,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = true,
  maxLines: Int = if (singleLine) 1 else 6,
  minLines: Int = 1,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  shape: Shape = CircleShape,
  contentPadding: PaddingValues = PaddingValues(
    top = 20.dp,
    bottom = 22.dp,
    start = 0.dp,
    end = 24.dp
  ),
  cursorColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
  colors: TextFieldColors = TextFieldDefaults.colors(
//        focusedTextColor = MaterialTheme.colorScheme.onBackground,
    focusedContainerColor = MaterialTheme.colorScheme.background,
    focusedIndicatorColor = Color.Transparent,
    focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,

//        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    unfocusedContainerColor = MaterialTheme.colorScheme.background,
    unfocusedIndicatorColor = Color.Transparent,
    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),

//        errorTextColor = MaterialTheme.colorScheme.error,
    errorContainerColor = MaterialTheme.colorScheme.errorContainer,
    errorIndicatorColor = Color.Transparent,
    errorLeadingIconColor = MaterialTheme.colorScheme.onError,
  ),
) {
  val textColor = textStyle.color.takeOrElse {
    textColor(enabled, isError, interactionSource).value
  }

  val mergedTextStyle = textStyle.copy(color = textColor)

  val handleColor =
    if (isError) MaterialTheme.colorScheme.onError
    else MaterialTheme.colorScheme.primary

  val backgroundColor =
    if (isError) MaterialTheme.colorScheme.onError.copy(alpha = 0.2f)
    else MaterialTheme.colorScheme.background.copy(alpha = 0.5f)

  CompositionLocalProvider(
    LocalTextSelectionColors provides TextSelectionColors(
      handleColor = handleColor,
      backgroundColor = backgroundColor,
    )
  ) {
    BasicTextField(
      value = value,
      modifier = modifier,
      onValueChange = onValueChange,
      enabled = enabled,
      readOnly = readOnly,
      textStyle = mergedTextStyle,
      cursorBrush = SolidColor(cursorColor),
      visualTransformation = visualTransformation,
      keyboardOptions = keyboardOptions,
      keyboardActions = keyboardActions,
      interactionSource = interactionSource,
      singleLine = singleLine,
      maxLines = maxLines,
      minLines = minLines,
      decorationBox = @Composable { innerTextField ->
        TextFieldDefaults.DecorationBox(
          value = value.text,
          visualTransformation = visualTransformation,
          innerTextField = innerTextField,
          placeholder = placeholder,
          label = label,
          leadingIcon = leadingIcon,
          trailingIcon = trailingIcon,
          prefix = prefix,
          suffix = suffix,
          supportingText = supportingText,
          shape = shape,
          contentPadding = contentPadding,
          singleLine = singleLine,
          enabled = enabled,
          isError = isError,
          interactionSource = interactionSource,
          colors = colors
        )

        if (readOnly && onClickIfReadOnly != null) {
          Box(
            modifier = modifier
              .fillMaxSize()
              .clickable(interactionSource = null, indication = null, onClick = onClickIfReadOnly)
          )
        }
      }
    )
  }
}

@Composable
private fun textColor(
  enabled: Boolean,
  isError: Boolean,
  interactionSource: InteractionSource,
): State<Color> {
  val focused by interactionSource.collectIsFocusedAsState()

  val targetValue = when {
    !enabled -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
    isError -> MaterialTheme.colorScheme.error
    focused -> MaterialTheme.colorScheme.onBackground
    else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
  }
  return rememberUpdatedState(targetValue)
}

@Composable
fun InputLinkUnfocusedPreview() {
  InputLink(
    title = null,
    url = "https://www.google.com/search?q=android+studio+how",
    linkEditor = isSystemInDarkTheme()
  )
}

@Composable
fun InputLinkFocusedPreview() {
  InputLink(
    title = null,
    url = null,
  )
}

@Composable
fun LinkTextFieldPreview() {
  LinkTextField(
    value = TextFieldValue(text = "Hello Compose"),
    onValueChange = {},
  )
}