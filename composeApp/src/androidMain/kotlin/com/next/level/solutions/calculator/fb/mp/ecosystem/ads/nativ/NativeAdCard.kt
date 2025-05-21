package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ

import android.view.LayoutInflater
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.next.level.solutions.calculator.fb.mp.R
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview
import kotlinx.coroutines.flow.Flow

@Composable
fun NativeAdCard(
  size: NativeSize?,
  ad: Flow<NativeAd?>?,
  modifier: Modifier,
  loadAtDispose: Boolean,
  color: Color?,
  dividerSize: DividerSize,
  loadNative: () -> Unit,
) {
  HorizontalDivider(thickness = dividerSize.top)

  Content(
    size = size,
    modifier = modifier,
    loadAtDispose = loadAtDispose,
    color = color ?: MaterialTheme.colorScheme.onBackground.copy(alpha = 0f),
    loadNative = loadNative,
    ad = ad,
  )

  HorizontalDivider(thickness = dividerSize.bottom)
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
  ad: Flow<NativeAd?>? = null,
  size: NativeSize? = NativeSize.Large,
  loadAtDispose: Boolean = true,
  color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0f),
  loadNative: () -> Unit = {},
) {
  size ?: return

  Ad(
    loadAtDispose = loadAtDispose,
    loadNative = loadNative,
    modifier = modifier,
    color = color,
    size = size,
    ad = ad,
  )
}

@Composable
private fun Ad(
  modifier: Modifier = Modifier,
  ad: Flow<NativeAd?>? = null,
  size: NativeSize = NativeSize.Large,
  loadAtDispose: Boolean = true,
  color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0f),
  loadNative: () -> Unit = {},
) {
  val nativeAdState: State<NativeAd?>? = ad?.collectAsStateWithLifecycle(
    initialValue = null
  )

  val nativeAd: NativeAd? by remember {
    derivedStateOf { nativeAdState?.value }
  }

  AdCardContainer(
    modifier = modifier,
    size = size,
    color = color,
    content = {
      when {
        nativeAd == null -> LinearProgressIndicator(
          modifier = Modifier
            .clip(shape = CircleShape)
        )

        else -> AdCard(
          loadNative = loadNative,
          size = size,
          loadAtDispose = loadAtDispose,
          nativeAdState = nativeAdState,
        )
      }
    }
  )
}

@Composable
private fun AdCardContainer(
  modifier: Modifier = Modifier,
  size: NativeSize = NativeSize.Large,
  color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0f),
  content: @Composable BoxScope.() -> Unit
) {
  val minHeight = remember(key1 = size) {
    when (size) {
      NativeSize.Small -> 100.dp
      NativeSize.Large -> 270.dp
      NativeSize.Adaptive -> 270.dp
    }
  }

  Box(
    contentAlignment = Alignment.Center,
    content = content,
    modifier = modifier
      .fillMaxWidth()
      .height(intrinsicSize = IntrinsicSize.Min)
      .heightIn(min = minHeight)
      .clip(shape = RoundedCornerShape(size = 0.dp))
      .background(color = color)
      .animateContentSize()
  )
}

@Composable
private fun AdCard(
  loadNative: () -> Unit,
  size: NativeSize = NativeSize.Large,
  loadAtDispose: Boolean = true,
  nativeAdState: State<NativeAd?>?,
) {
  if (loadAtDispose) {
    DisposableEffect(
      key1 = Unit,
      effect = { onDispose { loadNative() } },
    )
  }

  val layout = remember {
    when (size) {
      NativeSize.Small -> R.layout.native_ad_small
      NativeSize.Large -> R.layout.native_ad_large
      NativeSize.Adaptive -> R.layout.native_ad_adaptive
    }
  }

  val nativeAd: NativeAd? by remember {
    derivedStateOf { nativeAdState?.value }
  }

  val textColor = MaterialTheme.colorScheme.onBackground

  AndroidView(
    factory = { context ->
      LayoutInflater
        .from(context)
        .inflate(layout, null) as NativeAdView
    },
    update = { adView ->
      val adLabel = adView.findViewById<TextView>(R.id.ad_label)
      val adIconView = adView.findViewById<ImageView>(R.id.ad_app_icon)
      val adHeadlineView = adView.findViewById<TextView>(R.id.ad_headline)
      val adBodyView = adView.findViewById<TextView>(R.id.ad_body)
      val adBodyBigView = adView.findViewById<TextView>(R.id.ad_body_big)
      val adCallToActionView = adView.findViewById<TextView>(R.id.ad_call_to_action)
      val adAdvertiserView = adView.findViewById<TextView>(R.id.ad_advertiser)
      val adMediaView = adView.findViewById<MediaView>(R.id.ad_media)

      adLabel?.setTextColor(textColor.toArgb())
      adHeadlineView?.setTextColor(textColor.toArgb())
      adBodyView?.setTextColor(textColor.toArgb())

      adView.headlineView = adHeadlineView
      adView.bodyView = adBodyView
      adView.callToActionView = adCallToActionView
      adView.iconView = adIconView
      adView.advertiserView = adAdvertiserView
      adView.mediaView = adMediaView

      nativeAd?.let { nativeAd ->
        adIconView?.setImageDrawable(nativeAd.icon?.drawable)
        adHeadlineView?.text = nativeAd.headline
        adBodyView?.text = nativeAd.body
        adBodyBigView?.text = nativeAd.body
        adCallToActionView?.text = nativeAd.callToAction
        adAdvertiserView?.text = nativeAd.advertiser
        adMediaView?.mediaContent = nativeAd.mediaContent

        when (nativeAd.mediaContent == null) {
          true -> adBodyView?.isVisible = false
          else -> adBodyBigView?.isVisible = false
        }

        adView.setNativeAd(nativeAd)
        adView.requestLayoutWithDelay(500)
      }
    },
    modifier = Modifier
      .fillMaxWidth()
  )
}

private fun View.requestLayoutWithDelay(delayMillis: Long) {
  fun ViewParent?.findAndroidComposeViewParent(): ViewParent? = when {
    this != null && this::class.java.simpleName == "AndroidComposeView" -> this
    this != null -> parent.findAndroidComposeViewParent()
    else -> null
  }

  post {
    val t = parent.findAndroidComposeViewParent()

    if (t == null) {
      postDelayed(delayMillis) {
        parent.findAndroidComposeViewParent()?.requestLayout()
      }
    } else {
      t.requestLayout()
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  AppThemePreview {
    Column(
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
    ) {
      Content(
        size = NativeSize.Small,
      )
    }
  }
}