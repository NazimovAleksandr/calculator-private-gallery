package com.next.level.solutions.calculator.fb.mp.ui.screen.create.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.create_notes
import calculator_fileblocking.composeapp.generated.resources.enter_title
import calculator_fileblocking.composeapp.generated.resources.notes
import calculator_fileblocking.composeapp.generated.resources.type_your_secret_note
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.text.field.AppTextField
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Tick
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CreateNotesContent(
  component: CreateNotesComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun CreateNotesContentPreview(
  modifier: Modifier = Modifier
) {
  Content(
    component = null,
    modifier = modifier
  )
}

@Composable
private fun Content(
  component: CreateNotesComponent?,
  modifier: Modifier = Modifier,
) {
  val focusRequester = remember { FocusRequester() }

  val model = component?.model?.subscribeAsState()

  val note by remember {
    derivedStateOf {
      model?.value?.note
    }
  }

  val titleValue = remember(key1 = note) { mutableStateOf(note?.name ?: "") }
  val noteValue = remember(key1 = note) { mutableStateOf(note?.note ?: "") }

  fun create() {
    component?.action(
      CreateNotesComponent.Action.Create(
        name = titleValue.value.trim(),
        note = noteValue.value.trim(),
      )
    )
  }

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
      .imePadding()
  ) {
    Toolbar(
      title = stringResource(
        resource = when (note) {
          null -> Res.string.create_notes
          else -> Res.string.notes
        }
      ),
      navAction = {
        component?.action(CreateNotesComponent.Action.Back)
      },
      menu = {
        Image(
          vector = MagicIcons.All.Tick,
          colorFilter = when (titleValue.value.isNotBlank() && noteValue.value.isNotBlank()) {
            true -> MaterialTheme.colorScheme.onBackground
            else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
          },
          modifier = Modifier
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(
              enabled = titleValue.value.isNotBlank() && noteValue.value.isNotBlank(),
              onClick = ::create,
            )
        )
      }
    )

    component?.nativeAdCard(
      size = NativeSize.Small,
    )

    Spacer(modifier = Modifier.height(height = 16.dp))

    AppTextField(
      initValue = note?.name ?: "",
      placeholder = Res.string.enter_title,
      onValueChange = { titleValue.value = it },
      singleLine = true,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .focusRequester(focusRequester = focusRequester)
    )

    Spacer(modifier = Modifier.height(height = 12.dp))

    AppTextField(
      initValue = note?.note ?: "",
      placeholder = Res.string.type_your_secret_note,
      onValueChange = { noteValue.value = it },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .weight(weight = 1f)
    )

    Spacer(modifier = Modifier.height(height = 16.dp))
  }
}