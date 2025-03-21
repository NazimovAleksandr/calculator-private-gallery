package com.next.level.solutions.calculator.fb.mp.ui.composable.bottom.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
  onDismissRequest: () -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
  shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
  containerColor: Color = MaterialTheme.colorScheme.background,
  dragHandle: @Composable () -> Unit = {
    Box(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .size(width = 32.dp, height = 4.dp)
        .clip(shape = CircleShape)
        .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
    )
  },
  content: @Composable ColumnScope.(dismissRequest: DismissRequest) -> Unit,
) {
  val scope = rememberCoroutineScope()

  BottomSheetContent(
    sheetState = sheetState,
    onDismissRequest = onDismissRequest,
    containerColor = containerColor,
    dragHandle = dragHandle,
    shape = shape,
    content = {
      content(
        object : DismissRequest {
          override fun dismiss() {
            scope
              .launch {
                sheetState.hide()
              }
              .invokeOnCompletion {
                if (!sheetState.isVisible) {
                  onDismissRequest()
                }
              }
          }
        }
      )
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContent(
  onDismissRequest: () -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
  shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
  containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
  dragHandle: @Composable () -> Unit = {
    Box(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .size(width = 32.dp, height = 4.dp)
        .clip(shape = CircleShape)
        .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
    )
  },
  content: @Composable ColumnScope.() -> Unit,
) {
  ModalBottomSheet(
    sheetState = sheetState,
    shape = shape,
    containerColor = containerColor,
    onDismissRequest = onDismissRequest,
    dragHandle = dragHandle,
    content = content,
  )

  LifecycleEventListener(
    onStop = onDismissRequest,
  )
}

interface DismissRequest {
  fun dismiss()
}