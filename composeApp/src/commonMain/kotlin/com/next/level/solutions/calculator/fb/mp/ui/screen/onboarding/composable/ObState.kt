package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.composable

import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.ob_1
import calculator_fileblocking.composeapp.generated.resources.ob_2
import calculator_fileblocking.composeapp.generated.resources.ob_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class ObState(
  val image: DrawableResource,
  val title: StringResource,
) {
  One(
    image = Res.drawable.ob_1,
    title = Res.string.ob_1,
  ),

  Two(
    image = Res.drawable.ob_2,
    title = Res.string.ob_2,
  ),

  Three(
    image = Res.drawable.ob_3,
    title = Res.string.ob_3,
  ),
}