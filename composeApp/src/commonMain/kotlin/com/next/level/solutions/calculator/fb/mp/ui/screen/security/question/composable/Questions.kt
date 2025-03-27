package com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.questions
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.ArrowDown
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringArrayResource

@Composable
internal inline fun Questions(
  initQuestion: String?,
  modifier: Modifier = Modifier,
  crossinline selected: (String) -> Unit,
) {
  val density = LocalDensity.current

  var menuVisible by remember {
    mutableStateOf(false)
  }

  var questionsValue: ImmutableList<String> = remember {
    persistentListOf()
  }

  if (questionsValue.isEmpty()) {
    questionsValue = stringArrayResource(Res.array.questions).toImmutableList()
  }

  var selectedQuestion by remember {
    mutableStateOf((initQuestion ?: questionsValue.first()))
  }

  var yOffset by remember {
    mutableIntStateOf(0)
  }

  val shapeForFirstItem = MaterialTheme.shapes.large.copy(
    bottomStart = CornerSize(0.dp),
    bottomEnd = CornerSize(0.dp),
  )

  val shapeForLastItem = MaterialTheme.shapes.large.copy(
    topStart = CornerSize(0.dp),
    topEnd = CornerSize(0.dp),
  )

  Box(
    contentAlignment = Alignment.TopStart,
    modifier = modifier
  ) {
    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clip(shape = MaterialTheme.shapes.large)
        .background(color = MaterialTheme.colorScheme.secondary)
        .clickable(onClick = { menuVisible = true })
        .onGloballyPositioned { yOffset = it.size.height }
        .padding(all = 24.dp)
    ) {
      Text(
        text = selectedQuestion,
        color = MaterialTheme.colorScheme.onSecondary,
        style = TextStyleFactory.FS16.w600(),
        modifier = Modifier
          .weight(weight = 1f)
          .padding(end = 16.dp)
      )

      Image(
        vector = MagicIcons.All.ArrowDown,
        colorFilter = MaterialTheme.colorScheme.primary,
        modifier = Modifier
      )
    }

    DropdownMenu(
      expanded = menuVisible,
      onDismissRequest = { menuVisible = false },
      offset = DpOffset.Zero.copy(y = with(density) { (-yOffset).toDp() } + (-8).dp, x = (-0.5).dp),
      shape = MaterialTheme.shapes.large,
      containerColor = Color.Transparent,
      shadowElevation = 0.dp,
      modifier = Modifier
        .padding(horizontal = 3.5.dp)
        .fillMaxWidth()
    ) {
      questionsValue
        .forEachIndexed { i, it ->
          val clip = when (i) {
            0 -> Modifier.clip(shape = shapeForFirstItem)
            questionsValue.size - 1 -> Modifier.clip(shape = shapeForLastItem)
            else -> Modifier
          }

          DropdownMenuItem(
            text = {
              Text(
                text = it,
                color = MaterialTheme.colorScheme.onSecondary,
                style = TextStyleFactory.FS16.w600(),
                modifier = Modifier
                  .heightIn(min = 72.dp)
                  .fillMaxWidth()
                  .then(clip)
                  .background(color = MaterialTheme.colorScheme.secondary)
                  .padding(all = 24.dp)
                  .wrapContentHeight()
              )
            },
            onClick = {
              selected(it)
              selectedQuestion = it
              menuVisible = false
            },
            modifier = Modifier
          )
        }
    }
  }

  LaunchedEffect(key1 = Unit) {
    selected(selectedQuestion)
  }

  LifecycleEventListener(
    onPause = { menuVisible = false }
  )
}