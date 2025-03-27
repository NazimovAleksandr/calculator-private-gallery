package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.ic_placeholder_icon
import calculator_privategallery.composeapp.generated.resources.link_copied
import coil3.Bitmap
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Copy
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Pencil
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Share
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import io.ktor.http.Url
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.BrowserLinkEditor(
  visible: Boolean,
  url: String?,
  pageIcon: State<Bitmap?>,
  title: State<String?>,
  onEditLink: () -> Unit,
  onDismiss: () -> Unit,
) {
  if (!visible) return

  val keyboardController = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  if (url != null) {
    val platformContext = LocalPlatformContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val pageIconValue by remember {
      derivedStateOf {
        pageIcon.value
      }
    }

    val titleValue by remember {
      derivedStateOf {
        title.value
      }
    }

    val cacheKey = Url(url.toString()).host

    val pageIconRequest = ImageRequest.Builder(platformContext)
      .data(pageIconValue)
      .memoryCacheKey(cacheKey)
      .diskCacheKey(cacheKey)
      .diskCachePolicy(CachePolicy.ENABLED)
      .memoryCachePolicy(CachePolicy.ENABLED)
      .build()

    val linkCopied = stringResource(Res.string.link_copied)

    Scaffold(
      snackbarHost = {
        SnackbarHost(
          hostState = snackbarHostState,
        )
      },
      modifier = Modifier
        .height(height = 130.dp)
    ) {
      Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .padding(top = 8.dp)
      ) {
        SubcomposeAsyncImage(
          model = pageIconRequest,
          contentDescription = null,
          loading = {
            Image(
              id = Res.drawable.ic_placeholder_icon,
              colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
              modifier = Modifier
            )
          },
          error = {
            Image(
              id = Res.drawable.ic_placeholder_icon,
              colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
              modifier = Modifier
            )
          },
          modifier = Modifier
            .size(size = 50.dp)
            .padding(all = 10.dp)
        )

        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.Start,
          modifier = Modifier
            .weight(weight = 1f)
            .heightIn(min = 48.dp)
            .clickable {
              keyboardController?.hide()
              focusManager.clearFocus()
              onDismiss()
            }
        ) {
          titleValue?.let {
            Text(
              text = it,
              style = TextStyleFactory.FS15.w400(),
              color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier
            )
          }

          Text(
            text = url,
            style = TextStyleFactory.FS14.w400(),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
          )
        }

        Image(
          vector = MagicIcons.All.Share,
          colorFilter = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier
            .padding(all = 2.dp)
            .clip(shape = CircleShape)
            .clickable(onClick = { PlatformExp.shareLink(titleValue, url) })
            .padding(all = 10.dp)
        )

        Image(
          vector = MagicIcons.All.Copy,
          colorFilter = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier
            .padding(all = 2.dp)
            .clip(shape = CircleShape)
            .clickable(onClick = {
              clipboardManager.setText(AnnotatedString(url))

              scope.launch {
                snackbarHostState.showSnackbar(message = linkCopied)
              }
            })
            .padding(all = 10.dp)
        )

        Image(
          vector = MagicIcons.All.Pencil,
          colorFilter = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier
            .padding(all = 2.dp)
            .clip(shape = CircleShape)
            .clickable(onClick = onEditLink)
            .padding(all = 10.dp)
        )
      }
    }
  }

  Box(
    modifier = Modifier
      .weight(weight = 1f)
      .fillMaxWidth()
      .pointerInput(key1 = Unit) {
        detectTapGestures {
          keyboardController?.hide()
          focusManager.clearFocus()
        }
      }
  )
}

@Composable
fun BrowserLinkEditorPreview() {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
  ) {
    BrowserLinkEditor(
      visible = true,
      url = "hhh",
      pageIcon = remember { mutableStateOf(null) },
      title = remember { mutableStateOf(null) },
      onEditLink = {},
      onDismiss = {},
    )
  }
}