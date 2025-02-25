package com.next.level.solutions.calculator.fb.mp.file.hider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.os.Environment.getExternalStorageDirectory
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Log
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.ParentFolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.VideoModelUI
import com.next.level.solutions.calculator.fb.mp.extensions.core.uppercaseFirstChar
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.reflect.KFunction8
import kotlin.time.measureTime

actual fun getFileHider(): FileHider {
  return MainActivity.fileHider?.invoke() ?: throw IllegalStateException("FileHider not initialized")
}

enum class FileData(val field: String) {
  Path(field = MediaStore.MediaColumns.DATA),
  Name(field = MediaStore.MediaColumns.TITLE),
  Folder(field = MediaStore.MediaColumns.BUCKET_DISPLAY_NAME),
  Size(field = MediaStore.MediaColumns.SIZE),
  DateAdded(field = MediaStore.MediaColumns.DATE_ADDED),
  DateModified(field = MediaStore.MediaColumns.DATE_MODIFIED),
  Duration(field = MediaStore.MediaColumns.DURATION),
}

class FileHiderImpl(
  private val context: Context,
) : FileHider {
  companion object {
    private const val TAG = "TAG_FILE_HIDER"
    const val FILES_DATA_FILE_NAME = "filesData.txt"
  }

  private val documents: File? = getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)
  private val externalStorage: String = getExternalStorageDirectory().absolutePath ?: ""

  private val dateFormat: SimpleDateFormat = SimpleDateFormat(
    "HH_mm_ss_SSS",
    Locale.getDefault(),
  )

  private val dateModifiedFormat: SimpleDateFormat = SimpleDateFormat(
    "MMM dd, yyyy",
    Locale.getDefault(),
  )

  private val dateHidden: String get() = dateFormat.format(System.currentTimeMillis())

  override fun hiddenFiles(
    fileType: FilePickerFileType,
  ): List<FileDataUI> {
    val apps = File(documents, ".apps").create()
    val folder = File(apps, fileType.name).create()
    return folder
      .files(withHidden = true)
      .map { file ->
        fileType.constructor().invoke(
          /* path = */ file.absolutePath,
          /* name = */ file.name,
          /* folder = */ file.folder(),
          /* size = */ file.length(),
          /* dateAdded = */ file.dateAdded().toString(),
          /* dateHidden = */ dateHidden,
          /* dateModified = */ file.lastModified().toString(),
          /* hiddenPath = */ null,
        )
      }
  }

  override fun visibleFiles(
    fileType: FilePickerFileType,
    viewType: FilePickerViewType,
  ): List<FileDataUI> {
    Log.d(TAG, "visibleFiles: ")

    return when (viewType) {
      FilePickerViewType.Gallery -> fileType
        .loadVisibleFiles(
          context = context,
        )

      FilePickerViewType.Folder -> fileType
        .loadVisibleFilesFormFolder(
          folder = externalStorage,
        )
    }
  }

  override fun visibleFiles(
    folder: String?,
    fromParent: Boolean,
    fileType: FilePickerFileType,
    callBack: (List<FileDataUI>) -> Unit,
  ) {
    val folderPath = when {
      fromParent -> folder?.let { File(it).parentFile?.absolutePath }
      else -> folder
    }

    fileType.loadVisibleFilesFormFolder(
      folder = folderPath ?: externalStorage,
      callBack = callBack,
    )
  }

  override suspend fun moveToHiddenFiles(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    return files.hide(
      fileType = fileType,
      constructor = fileType.constructor(),
    )
  }

  override suspend fun restoreFiles(
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    return files.restore()
  }

  override suspend fun moveToVisibleFiles(
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    return files.moveToVisible()
  }

  override suspend fun moveToDeletedFiles(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    return when (fileType) {
      FilePickerFileType.Note -> files

      FilePickerFileType.Trash,
      FilePickerFileType.File,
        -> {
        files.forEach {
          try {
            File(it.hiddenPath ?: it.path).delete()
          } catch (ignore: Exception) {
          }
        }

        files
      }

      else -> files.moveToDeletedFiles(
        fileType = fileType,
        constructor = fileType.constructor(),
      )
    }
  }

  override fun constructorForFileType(
    fileType: FilePickerFileType,
  ): KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> {
    return fileType.constructor()
  }

  private fun File.create(): File {
    return apply { if (!exists()) mkdirs() }
  }

  private fun File.files(
    fileType: FilePickerFileType? = null,
    withHidden: Boolean = false,
    sorted: Boolean = false,
  ): List<File> {
    var list = when (withHidden) {
      true -> listFiles()?.toList()
      else -> listFiles()?.filter { !it.isHidden }
    } ?: emptyList()

    list = when (fileType) {
      null -> list

      else -> list.filter { file ->
        when (fileType) {
          FilePickerFileType.Photo -> file.isImage()
          FilePickerFileType.Video -> file.isVideo()
          else -> true
        }
      }
    }

    list = when (sorted) {
      false -> list

      else -> list.sortedWith(
        comparator = compareBy(
          { !it.isDirectory },
          { it.name.lowercase() },
        ),
      )
    }

    return list
  }

  private fun FilePickerFileType.loadVisibleFiles(
    context: Context,
  ): List<FileDataUI> {
    var files: List<FileDataUI>

    val time = measureTime {
      files = uri()
        ?.cursor(context = context, fileType = this)
        ?.loadVisibleFiles(constructor = constructor())
        ?: emptyList()
    }

    Log.d(TAG, "loadVisibleFiles: files = ${files.size}")
    Log.d(TAG, "loadVisibleFiles: time = $time")

    return files
  }

  private fun FilePickerFileType.loadVisibleFilesFormFolder(
    folder: String,
    callBack: (List<FileDataUI>) -> Unit = {},
  ): List<FileDataUI> {
    val parentFolder = File(folder)

    val files: MutableList<FileDataUI> = mutableListOf(
      ParentFolderModelUI(
        path = parentFolder.absolutePath,
        name = parentFolder.name,
        folder = parentFolder.folder(),
      )
    )

    callBack(files)

    val time = measureTime {
      files.addAll(
        parentFolder
          .files(fileType = this, sorted = true)
          .loadVisibleFiles(
            fileType = this,
            constructor = constructor(),
            callBack = { callBack(files + it) },
          )
      )
    }

    Log.d(
      TAG,
      """
        loadVisibleFilesFormFolder:
            fileType = $this
            folder = $folder
            files = ${files.size}
            time = $time
      """.trimIndent(),
    )

    callBack(files)

    return files
  }

  private fun FilePickerFileType.uri(): Uri? {
    return when (this) {
      FilePickerFileType.Photo -> when (Build.VERSION.SDK_INT >= 29) {
        true -> MediaStore.Images.Media.getContentUri("external_primary")
        else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
      }

      FilePickerFileType.Video -> when (Build.VERSION.SDK_INT >= 29) {
        true -> MediaStore.Video.Media.getContentUri("external_primary")
        else -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
      }

      else -> null
    }
  }

  private fun FilePickerFileType.constructor(): KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> {
    return when (this) {
      FilePickerFileType.Photo -> ::PhotoModelUI
      FilePickerFileType.Video -> ::VideoModelUI
      FilePickerFileType.File -> ::FileModelUI
      FilePickerFileType.Trash -> ::TrashModelUI
      FilePickerFileType.Note -> ::TrashModelUI
    }
  }

  private fun Uri.cursor(
    context: Context,
    fileType: FilePickerFileType,
  ): Cursor? {
    val projection = FileData.entries.toMutableList()

    if (fileType != FilePickerFileType.Video) {
      projection.remove(FileData.Duration)
    }

    return context.contentResolver.query(
      /* uri = */ this,
      /* projection = */ projection.map { it.field }.toTypedArray(),
      /* selection = */ null,
      /* selectionArgs = */ null,
      /* sortOrder = */ "${FileData.DateAdded.field} DESC"
    )
  }

  private fun Cursor.loadVisibleFiles(
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI>,
  ): List<FileDataUI> {
    val files: MutableList<FileDataUI> = mutableListOf()

    use {
      while (moveToNext()) {
        val path: String = getString(FileData.Path.ordinal) ?: ""
        val name: String = getString(FileData.Name.ordinal) ?: ""
        val folder: String = getString(FileData.Folder.ordinal) ?: "0"
        val size: Long = getLong(FileData.Size.ordinal)
        val dateAdded: String = getString(FileData.DateAdded.ordinal) ?: ""
        val dateModified: String = getString(FileData.DateModified.ordinal) ?: ""

        val file = constructor.invoke(
          path,
          name,
          folder,
          size,
          dateAdded,
          dateHidden,
          dateModified,
          null,
        )

        if (file is VideoModelUI) {
          file.duration = getLong(FileData.Duration.ordinal).getMediaDurationString()
        }

        files.add(file)
      }
    }

    return files
  }

  private fun List<File>.loadVisibleFiles(
    fileType: FilePickerFileType,
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI>,
    callBack: (List<FileDataUI>) -> Unit,
  ): List<FileDataUI> {
    val list: MutableList<FileDataUI> = mutableListOf()
    var startTime = System.currentTimeMillis()

    forEach { file ->
      val fileData = when {
        file.isDirectory -> {
          FolderModelUI(
            path = file.absolutePath,
            name = file.name,
            folder = file.folder(),
            size = file.files(fileType = fileType).size.toLong(),
            dateModified = dateModifiedFormat.format(file.lastModified()).uppercaseFirstChar(),
          )
        }

        else -> {
          constructor.invoke(
            /* path = */ file.absolutePath,
            /* name = */ file.name,
            /* folder = */ file.folder(),
            /* size = */ file.length(),
            /* dateAdded = */ file.dateAdded().toString(),
            /* dateHidden = */ dateHidden,
            /* dateModified = */ file.lastModified().toString(),
            /* hiddenPath = */ null,
          )
        }
      }

      list.add(fileData)

      if (System.currentTimeMillis() - startTime >= 150) {
        startTime = System.currentTimeMillis()
        callBack(list)
      }
    }

    return list
  }

  private suspend fun List<FileDataUI>.hide(
    fileType: FilePickerFileType,
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI>,
  ): List<FileDataUI> {
    delay(10)

    val list = mutableListOf<FileDataUI>()

    forEach { file ->
      try {
        val source = File(file.path)
        val target = File(documents, ".apps/${fileType.name}/(${file.dateHidden})${source.name}")

        move(
          source = source,
          target = target,
        )

        addToFilesData(
          fileType = fileType,
          sourcePath = source.absolutePath,
          targetPath = target.absolutePath,
        )

        val newFile = constructor.invoke(
          /*path =*/ file.path,
          /*name =*/ file.name,
          /*folder =*/ file.folder,
          /*size =*/ file.size,
          /*dateAdded =*/ file.dateAdded,
          /*dateHidden =*/ file.dateHidden,
          /*dateModified =*/ file.dateModified,
          /*hiddenPath =*/ target.absolutePath,
        )

        if (newFile is VideoModelUI) {
          newFile.duration = file.duration
        }

        list.add(newFile)
      } catch (e: Exception) {
        Log.e(TAG, "hideFiles: $e")
      }
    }

    return list
  }

  private suspend fun List<FileDataUI>.moveToVisible(): List<FileDataUI> {
    delay(10)

    val list = mutableListOf<FileDataUI>()

    forEach { file ->
      try {
        file.hiddenPath?.let { hiddenPath ->
          val source = File(hiddenPath)
          val target = File(file.path)

          move(
            source = source,
            target = target,
          )

//          removeFromFilesData(
//            fileType = file.fileType,
//            source = source,
//            target = target,
//          )

          list.add(file)
        }
      } catch (e: Exception) {
        Log.e(TAG, "moveToVisible: $e")
      }
    }

    return list
  }

  private suspend fun List<FileDataUI>.moveToDeletedFiles(
    fileType: FilePickerFileType,
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI>,
  ): List<FileDataUI> {
    delay(10)

    val list = mutableListOf<FileDataUI>()

    forEach { file ->
      try {
        file.hiddenPath?.let { hiddenPath ->
          val source = File(hiddenPath)

          val target = File(
            hiddenPath.replace(
              ".apps/${fileType.name}",
              ".apps/${FilePickerFileType.Trash.name}",
            )
          )

          move(
            source = source,
            target = target,
          )

          addToFilesData(
            fileType = FilePickerFileType.Trash,
            sourcePath = source.absolutePath,
            targetPath = target.absolutePath,
          )

//          removeFromFilesData(
//            fileType = file.fileType,
//            source = source,
//            target = target,
//          )

          val newFile = constructor.invoke(
            /*path =*/ file.path,
            /*name =*/ file.name,
            /*folder =*/ file.folder,
            /*size =*/ file.size,
            /*dateAdded =*/ file.dateAdded,
            /*dateHidden =*/ file.dateHidden,
            /*dateModified =*/ file.dateModified,
            /*hiddenPath =*/ target.absolutePath,
          )

          if (newFile is VideoModelUI) {
            newFile.duration = file.duration
          }

          list.add(newFile)
        }
      } catch (e: Exception) {
        Log.e(TAG, "moveToDeletedFiles: $e")
      }
    }

    return list
  }

  private suspend fun List<FileDataUI>.restore(): List<FileDataUI> {
    delay(10)

    val list = mutableListOf<FileDataUI>()

    forEach { file ->
      try {
        file.hiddenPath?.let { hiddenPath ->
          val source = File(hiddenPath)

          val target = File(
            hiddenPath.replace(
              ".apps/${FilePickerFileType.Trash.name}",
              ".apps/${file.fileType.name}",
            )
          )

          move(
            source = source,
            target = target,
          )

          addToFilesData(
            fileType = file.fileType,
            sourcePath = source.absolutePath,
            targetPath = target.absolutePath,
          )

//          removeFromFilesData(
//            fileType = FilePickerFileType.RecycleBin,
//            source = source,
//            target = target,
//          )

          val newFile = file.fileType.constructor().invoke(
            /*path =*/ file.path,
            /*name =*/ file.name,
            /*folder =*/ file.folder,
            /*size =*/ file.size,
            /*dateAdded =*/ file.dateAdded,
            /*dateHidden =*/ file.dateHidden,
            /*dateModified =*/ file.dateModified,
            /*hiddenPath =*/ target.absolutePath,
          )

          if (newFile is VideoModelUI) {
            newFile.duration = file.duration
          }

          list.add(newFile)
        }
      } catch (e: Exception) {
        Log.e(TAG, "restore: $e")
      }
    }

    return list
  }

  private fun move(
    source: File,
    target: File,
  ) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Files.move(
        /* source = */ source.toPath(),
        /* target = */ target.toPath(),
      )
    } else {
      if (!target.exists()) {
        try {
          target.createNewFile()
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }

      source.renameTo(target)
    }
  }

  private fun addToFilesData(
    fileType: FilePickerFileType,
    sourcePath: String,
    targetPath: String,
  ) {
    val filesData = File(documents, ".apps/${fileType.name}/${FILES_DATA_FILE_NAME}")

    if (!filesData.exists()) filesData.createNewFile()

    filesData.appendText("${sourcePath}:${targetPath}\n\n")
  }

//  private fun removeFromFilesData(
//    fileType: FilePickerFileType,
//    source: File,
//    target: File,
//  ) {
//    val filesData = File(documents, ".apps/${fileType.name}/${FILES_DATA_FILE_NAME}")
//
//    if (!filesData.exists()) return
//
//    val removingLine = "${target.absolutePath}:${source.absolutePath}"
//
//    val lines = filesData.readLines().filter {
//      it != removingLine
//    }
//
//    filesData.delete()
//    filesData.createNewFile()
//
//    val fileWriter = FileWriter(filesData, true)
//
//    lines.forEach {
//      fileWriter.append(it)
//      fileWriter.append("\n\n")
//    }
//
//    fileWriter.flush()
//    fileWriter.close()
//  }

  private fun File.folder(): String {
    return parentFile?.name ?: "null"
  }

  private fun File.dateAdded(): Long {
    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Files
        .readAttributes(toPath(), BasicFileAttributes::class.java)
        .creationTime()
        .toMillis()
    } else {
      lastModified()
    }

    return date
  }

  private fun File.isImage(): Boolean {
    val mimeType = URLConnection.guessContentTypeFromName(absolutePath)
    return mimeType == null || mimeType.startsWith("image")
  }

  private fun File.isVideo(): Boolean {
    val mimeType = URLConnection.guessContentTypeFromName(absolutePath)
    return mimeType == null || mimeType.startsWith("video")
  }

  private fun Long.getMediaDurationString(): String {
    val hours: Long = TimeUnit.MILLISECONDS.toHours(this)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(this) - hours * 60
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(this) - minutes * 60 - hours * 3600

    var time = when {
      "$minutes".length == 1 && "$seconds".length == 1 -> "0$minutes:0$seconds"

      "$minutes".length == 1 -> "0$minutes:$seconds"
      "$seconds".length == 1 -> "$minutes:0$seconds"

      else -> "$minutes:$seconds"
    }

    if (hours > 0) {
      time = "$hours:$time"
    }

    return time
  }
}