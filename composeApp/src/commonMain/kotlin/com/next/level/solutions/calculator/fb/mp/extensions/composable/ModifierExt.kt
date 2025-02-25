package com.next.level.solutions.calculator.fb.mp.extensions.composable

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.OverlayClip
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize.Companion.contentSize
import androidx.compose.animation.SharedTransitionScope.SharedContentState
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@Stable
@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.sharedElementExt(
  key: Any,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  boundsTransform: BoundsTransform = DefaultBoundsTransform,
  placeHolderSize: PlaceHolderSize = contentSize,
  renderInOverlayDuringTransition: Boolean = true,
  zIndexInOverlay: Float = 0f,
  clipInOverlayDuringTransition: OverlayClip = ParentClip,
) = let {
  if (sharedTransitionScope != null && animatedVisibilityScope != null) {
    with(sharedTransitionScope) {
      it.sharedElement(
        state = rememberSharedContentState(key = key),
        animatedVisibilityScope = animatedVisibilityScope,
        boundsTransform = boundsTransform,
        placeHolderSize = placeHolderSize,
        zIndexInOverlay = zIndexInOverlay,
        renderInOverlayDuringTransition = renderInOverlayDuringTransition,
        clipInOverlayDuringTransition = clipInOverlayDuringTransition,
      )
    }
  } else {
    it
  }
}

@ExperimentalSharedTransitionApi
private val DefaultBoundsTransform = BoundsTransform { _, _ -> DefaultSpring }

private val DefaultSpring = spring(
  stiffness = StiffnessMediumLow,
  visibilityThreshold = Rect.VisibilityThreshold
)

@ExperimentalSharedTransitionApi
private val ParentClip: OverlayClip =
  object : OverlayClip {
    override fun getClipPath(
      state: SharedContentState,
      bounds: Rect,
      layoutDirection: LayoutDirection,
      density: Density,
    ): Path? {
      return state.parentSharedContentState?.clipPathInOverlay
    }
  }
