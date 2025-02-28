package com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.files
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.toUri
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.VideoModelUI
import com.next.level.solutions.calculator.fb.mp.expect.DateFormat
import com.next.level.solutions.calculator.fb.mp.expect.getDateFormat
import com.next.level.solutions.calculator.fb.mp.extensions.composable.sharedElementExt
import com.next.level.solutions.calculator.fb.mp.extensions.core.CacheParams
import com.next.level.solutions.calculator.fb.mp.extensions.core.imageRequest
import com.next.level.solutions.calculator.fb.mp.extensions.core.uppercaseFirstChar
import com.next.level.solutions.calculator.fb.mp.ui.composable.check.box.CheckBox
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.ArrowBack
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.File
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Folder
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Note
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun Gallery(
  modifier: Modifier,
  files: State<ImmutableList<FileDataUI>>,
  contentPadding: PaddingValues,
  currentMode: State<FilePickerMode>,
  selectedFiles: State<List<FileDataUI>>,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  onLongClick: (FileDataUI) -> Unit,
  onTap: (FileDataUI) -> Unit,
) {
  GalleryContent(
    modifier = modifier,
    files = files,
    contentPadding = contentPadding,
    currentMode = currentMode,
    selectedFiles = selectedFiles,
    sharedTransitionScope = sharedTransitionScope,
    animatedVisibilityScope = animatedVisibilityScope,
    onLongClick = onLongClick,
    onTap = onTap,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryContentPreview() {
  val list = mutableListOf<PhotoModelUI>().apply {
    repeat(10) {
      add(
        PhotoModelUI(
          path = "$it/Corrin",
          name = "PhotoModelUI",
          folder = "Kassy",
          size = 8933L,
          dateAdded = "1733156968",
          dateHidden = "Dec 04, 2024",
          dateModified = "1733156968",
        )
      )
    }
  }.toImmutableList()

  GalleryContent (
    modifier = Modifier,
    files = remember { mutableStateOf(list) },
    contentPadding = PaddingValues(0.dp),
    currentMode = remember { mutableStateOf(FilePickerMode.Select) },
    selectedFiles = remember { mutableStateOf(listOf()) },
    onLongClick = {},
    onTap = {},
    sharedTransitionScope = null,
    animatedVisibilityScope = null,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GalleryContent(
  modifier: Modifier,
  files: State<ImmutableList<FileDataUI>>,
  contentPadding: PaddingValues,
  currentMode: State<FilePickerMode>,
  selectedFiles: State<List<FileDataUI>>,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  onLongClick: (FileDataUI) -> Unit,
  onTap: (FileDataUI) -> Unit,
) {
  val selected by remember {
    derivedStateOf {
      selectedFiles.value
    }
  }

  val filesList by remember {
    derivedStateOf {
      files.value
    }
  }

  LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
    contentPadding = contentPadding,
    content = {
      items(
        items = filesList,
        key = { it.path }
      ) { file ->
        GalleryCard(
          file = file,
          mode = currentMode,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
          selected = selected.contains(file),
          onLongClick = { onLongClick(file) },
          onTap = { onTap(file) },
        )
      }
    },
    modifier = modifier
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private inline fun GalleryCard(
  file: FileDataUI,
  mode: State<FilePickerMode>,
  selected: Boolean,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  crossinline onLongClick: () -> Unit,
  crossinline onTap: () -> Unit,
) {
  val platformContext: PlatformContext = LocalPlatformContext.current
  val scope = rememberCoroutineScope()

  val imageRequest = remember {
    platformContext.imageRequest(
      data = file.hiddenPath?.toUri() ?: file.path.toUri(),
      cacheParams = CacheParams(
        key = file.name,
        fileQuality = 50,
        scope = scope,
      ),
    )
  }

  val selectMode by remember {
    derivedStateOf {
      mode.value == FilePickerMode.Select
    }
  }

  Box(
    contentAlignment = Alignment.TopEnd,
    modifier = Modifier
      .aspectRatio(ratio = 1f)
      .pointerInput(Unit) {
        detectTapGestures(
          onLongPress = { onLongClick() },
          onTap = { onTap() },
        )
      }
  ) {
    AsyncImage(
      model = imageRequest,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .sharedElementExt(
          key = file.hiddenPath ?: file.path,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
        )
        .aspectRatio(ratio = 1f)
        .background(color = MaterialTheme.colorScheme.background)
        .background(color = Color.Gray.copy(alpha = 0.3f))
    )

    CheckBox(
      checked = selected,
      visible = selectMode,
    )

    if (file.fileType == FilePickerFileType.Video) {
      Text(
        text = file.duration,
        style = TextStyleFactory.FS10.w400(),
        modifier = Modifier
          .padding(all = 4.dp)
          .clip(shape = MaterialTheme.shapes.small)
          .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f))
          .padding(all = 4.dp)
          .align(alignment = Alignment.BottomEnd)
      )
    }
  }
}

@Composable
internal fun Folder(
  modifier: Modifier,
  files: State<ImmutableList<FileDataUI>>,
  contentPadding: PaddingValues,
  currentMode: State<FilePickerMode>,
  selectedFiles: State<List<FileDataUI>>,
  onLongClick: (FileDataUI) -> Unit,
  onTap: (FileDataUI) -> Unit,
) {
  FolderContent(
    modifier = modifier,
    files = files,
    contentPadding = contentPadding,
    currentMode = currentMode,
    selectedFiles = selectedFiles,
    onLongClick = onLongClick,
    onTap = onTap,
  )
}

@Composable
fun FolderContentPreview() {
  val list = mutableListOf<FileDataUI>().apply {
    add(
      FolderModelUI(
        path = "0/Corrin",
        name = "PhotoModelUI",
        folder = "Kassy",
        size = 8933L,
        dateAdded = "1733156968",
        dateHidden = "Dec 04, 2024",
        dateModified = "1733156968",
      )
    )
    add(
      PhotoModelUI(
        path = "1/Corrin",
        name = "PhotoModelUI",
        folder = "Kassy",
        size = 8933L,
        dateAdded = "1733156968",
        dateHidden = "Dec 04, 2024",
        dateModified = "1733156968",
      )
    )
    add(
      VideoModelUI(
        path = "2/Corrin",
        name = "PhotoModelUI",
        folder = "Kassy",
        size = 8933L,
        dateAdded = "1733156968",
        dateHidden = "Dec 04, 2024",
        dateModified = "1733156968",
      )
    )
    add(
      FileModelUI(
        path = "3/Corrin",
        name = "PhotoModelUI",
        folder = "Kassy",
        size = 8933L,
        dateAdded = "1733156968",
        dateHidden = "Dec 04, 2024",
        dateModified = "1733156968",
      )
    )
  }.toImmutableList()

  FolderContent (
    modifier = Modifier,
    files = remember { mutableStateOf(list) },
    contentPadding = PaddingValues(0.dp),
    currentMode = remember { mutableStateOf(FilePickerMode.Select) },
    selectedFiles = remember { mutableStateOf(listOf()) },
    onLongClick = {},
    onTap = {},
  )
}

@Composable
private fun FolderContent(
  modifier: Modifier,
  files: State<ImmutableList<FileDataUI>>,
  contentPadding: PaddingValues,
  currentMode: State<FilePickerMode>,
  selectedFiles: State<List<FileDataUI>>,
  onLongClick: (FileDataUI) -> Unit,
  onTap: (FileDataUI) -> Unit,
) {
  val selected by remember {
    derivedStateOf {
      selectedFiles.value
    }
  }

  val dateModifiedFormat = remember {
    getDateFormat("MMM dd, yyyy")
  }

  val filesList by remember {
    derivedStateOf {
      files.value
    }
  }

  LazyColumn(
    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    horizontalAlignment = Alignment.Start,
    contentPadding = contentPadding,
    modifier = modifier,
    content = {
      items(
        items = filesList,
        key = { it.path }
      ) { file ->
        FolderCard(
          file = file,
          mode = currentMode,
          dateModifiedFormat = dateModifiedFormat,
          selected = selected.contains(file),
          onLongClick = { onLongClick(file) },
          onTap = { onTap(file) },
        )
      }
    },
  )
}

@Composable
private inline fun FolderCard(
  file: FileDataUI,
  mode: State<FilePickerMode>,
  dateModifiedFormat: DateFormat,
  selected: Boolean = false,
  crossinline onLongClick: () -> Unit,
  crossinline onTap: () -> Unit,
) {
  val selectMode by remember {
    derivedStateOf {
      mode.value == FilePickerMode.Select
    }
  }

  Row(
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .background(color = MaterialTheme.colorScheme.secondaryContainer)
      .padding(vertical = 14.dp)
      .padding(start = 16.dp, end = 8.dp)
      .pointerInput(Unit) {
        detectTapGestures(
          onLongPress = { onLongClick() },
          onTap = { onTap() },
        )
      }
  ) {
    Image(
      vector = when (file) {
        is FolderModelUI -> MagicIcons.All.Folder
        is PhotoModelUI -> MagicIcons.All.Image
        is VideoModelUI -> MagicIcons.All.File
        is FileModelUI -> MagicIcons.All.File
        else -> MagicIcons.All.Note
      },
      modifier = Modifier
    )

    Spacer(modifier = Modifier.width(width = 24.dp))

    Column(
      verticalArrangement = Arrangement.spacedBy(space = 5.dp),
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      Text(
        text = file.name,
        style = TextStyleFactory.FS16.w600(),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
      )

      Text(
        text = when (file) {
          is FolderModelUI -> "${file.size} ${stringResource(Res.string.files)}, ${file.dateModified}"
          else -> dateModifiedFormat.format(file.dateModified.toLong()).uppercaseFirstChar()
        },
        style = TextStyleFactory.FS12.w400(),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
          .graphicsLayer { alpha = 0.7f }
      )
    }

    Spacer(
      modifier = Modifier
        .width(width = 10.dp)
        .height(height = 40.dp)
    )

    when (file) {
      is FolderModelUI -> Image(
        vector = MagicIcons.All.ArrowBack,
        modifier = Modifier
          .rotate(180f)
          .padding(all = 8.dp)
      )

      else -> CheckBox(
        checked = selected,
        visible = selectMode,
      )
    }
  }
}