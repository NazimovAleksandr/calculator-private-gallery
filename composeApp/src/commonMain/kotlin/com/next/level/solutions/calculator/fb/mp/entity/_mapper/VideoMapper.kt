package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.VideoModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.VideoModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

fun VideoModelDB.toUI(): VideoModelUI = VideoModelUI(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
).apply {
  duration = this@toUI.duration
}

fun VideoModelUI.toDB(): VideoModelDB = VideoModelDB(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
  duration = when (duration) {
//    "" -> updateDuration()
    else -> duration
  },
)

fun VideoModelUI.toTrashDB(): TrashModelDB = TrashModelDB(
  type = FilePickerFileType.Video.name,
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
  duration = duration,
)

//private fun VideoModelUI.updateDuration(): String {
//  MediaMetadataRetriever().apply {
//    setDataSource(hiddenPath)
//
//    val time: Long = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0
//
//    val hours: Long = TimeUnit.MILLISECONDS.toHours(time)
//    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(time) - hours * 60
//    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(time) - minutes * 60 - hours * 3600
//
//    var duration = when {
//      "$minutes".length == 1 && "$seconds".length == 1 -> "0$minutes:0$seconds"
//
//      "$minutes".length == 1 -> "0$minutes:$seconds"
//      "$seconds".length == 1 -> "$minutes:0$seconds"
//
//      else -> "$minutes:$seconds"
//    }
//
//    if (hours > 0) {
//      duration = "$hours:$time"
//    }
//
//    return duration
//  }
//}