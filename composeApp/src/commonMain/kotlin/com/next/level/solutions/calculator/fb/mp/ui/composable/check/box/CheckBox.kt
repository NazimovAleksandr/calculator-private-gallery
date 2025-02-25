package com.next.level.solutions.calculator.fb.mp.ui.composable.check.box

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.CheckOff
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.CheckOn

@Composable
fun CheckBox(
  checked: Boolean,
  visible: Boolean,
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
) {
  if (!visible) return

  Image(
    vector = if (checked) MagicIcons.All.CheckOn else MagicIcons.All.CheckOff,
    modifier = onClick?.let { modifier.clickable(onClick = it) } ?: modifier
  )
}