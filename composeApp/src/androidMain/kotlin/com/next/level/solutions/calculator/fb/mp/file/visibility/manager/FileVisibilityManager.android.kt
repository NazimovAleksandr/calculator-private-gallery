package com.next.level.solutions.calculator.fb.mp.file.visibility.manager

import android.content.Context
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.os.Environment.getExternalStorageDirectory
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import com.google.gson.Gson
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.ParentFolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.VideoModelUI
import com.next.level.solutions.calculator.fb.mp.extensions.core.uppercaseFirstChar
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.coroutines.delay
import org.json.JSONObject
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

enum class FileData(val field: String) {
  Path(field = MediaStore.MediaColumns.DATA),
  Name(field = MediaStore.MediaColumns.TITLE),
  Folder(field = MediaStore.MediaColumns.BUCKET_DISPLAY_NAME),
  Size(field = MediaStore.MediaColumns.SIZE),
  DateAdded(field = MediaStore.MediaColumns.DATE_ADDED),
  DateModified(field = MediaStore.MediaColumns.DATE_MODIFIED),
  Duration(field = MediaStore.MediaColumns.DURATION),
}

class FileVisibilityManagerImpl(
  private val context: Context,
) : FileVisibilityManager {
  @Suppress("PrivatePropertyName")
  private val TAG = this::class.simpleName.toString()

  private val fileData = "file_data.ms"
  private val hiddenDirectory = ".HiddenDirectory_Calculator"

  private val documents: File? = getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)
  private val externalStorage: String = getExternalStorageDirectory().absolutePath ?: ""

  private val dateFormat: SimpleDateFormat = SimpleDateFormat(
    /* pattern = */ "HH_mm_ss_SSS",
    /* locale = */ Locale.getDefault(),
  )

  private val dateModifiedFormat: SimpleDateFormat = SimpleDateFormat(
    /* pattern = */ "MMM dd, yyyy",
    /* locale = */ Locale.getDefault(),
  )

  private val dateHidden: String get() = dateFormat.format(System.currentTimeMillis())

  override fun checkInvisibleFiles(): Boolean {
    PickerType.entries.forEach {
      val apps = File(documents, hiddenDirectory).create()
      val folder = File(apps, it.name).create()

      val state = folder
        .files(withHidden = true)
        .isNotEmpty()

      if (state) return true
    }

    return false
  }

  override fun invisibleFiles(
    fileType: PickerType,
    forRestore: Boolean,
  ): List<FileDataUI> {
    val apps = File(documents, hiddenDirectory).create()
    val folder = File(apps, fileType.name).create()

    var constructor = when (fileType) {
      PickerType.Trash -> null
      else -> fileType.constructor()
    }

    var files: List<FileDataUI> = emptyList()

    val fileDataJson = fileDataFile(fileType).fileDataJson()

    val time = measureTime {
      files = folder
        .files(withHidden = true)
        .mapNotNull { file ->
          if (constructor == null) {
            constructor = when {
              file.isImage() -> ::PhotoModelUI
              file.isVideo() -> ::VideoModelUI
              else -> ::FileModelUI
            }
          }

          var pathFormJson = ""

          for (key in fileDataJson.keys()) {
            val value = try {
              fileDataJson.get(key)
            } catch (e: Exception) {
              null
            }

            if (file.absolutePath == value) {
              pathFormJson = key
            }
          }

          val path = when (forRestore) {
            true -> pathFormJson
            else -> file.absolutePath
          }

          val hiddenPath = when (forRestore) {
            true -> file.absolutePath
            else -> null
          }

          val name = when (forRestore) {
            true -> file.name.substringAfter(")")
            else -> file.name
          }

          constructor?.invoke(
            /* path = */ path,
            /* name = */ name,
            /* folder = */ file.folder(),
            /* size = */ file.length(),
            /* dateAdded = */ file.dateAdded().toString(),
            /* dateHidden = */ file.name.substringAfter("(").substringBefore(")"),
            /* dateModified = */ file.lastModified().toString(),
            /* hiddenPath = */ hiddenPath,
          ).also {
            if (it is VideoModelUI) {
              it.duration = file.path.getMediaDuration()
            }
          }
        }
    }

    Logger.d(TAG, "invisibleFiles: fileType = $fileType")
    Logger.d(TAG, "invisibleFiles: files = ${files.size}")
    Logger.d(TAG, "invisibleFiles: time = $time")

    return files
  }

  override fun visibleFiles(
    fileType: PickerType,
    viewType: PickerMode,
  ): List<FileDataUI> {
    return when (viewType) {
      PickerMode.Gallery -> fileType
        .loadVisibleFiles(
          context = context,
        )

      PickerMode.Folder -> fileType
        .loadVisibleFilesFormFolder(
          folder = externalStorage,
        )
    }
  }

  override fun visibleFiles(
    folder: String?,
    fromParent: Boolean,
    fileType: PickerType,
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

  override suspend fun moveToInvisibleFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    files.toInvisibleFiles(
      fileType = fileType,
      callBack = callBack,
    )
  }

  override suspend fun moveToVisibleFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    files.toVisibleFiles(
      fileType = fileType,
      callBack = callBack,
    )
  }

  override suspend fun moveToDeletedFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    when (fileType) {
      PickerType.Trash -> files.delete(callBack = callBack)
      else -> files.toDeletedFiles(fileType = fileType, callBack = callBack)
    }
  }

  override suspend fun restoreToInvisibleFiles(
    files: List<TrashModelUI>,
    callBack: suspend (List<TrashModelUI>) -> Unit,
  ) {
    files.toInvisibleFiles(callBack = callBack)
  }

  private fun File.create(): File {
    return apply { if (!exists()) mkdirs() }
  }

  private fun File.files(
    fileType: PickerType? = null,
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
          PickerType.Photo -> file.isImage()
          PickerType.Video -> file.isVideo()
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

    return list.filter { it.name != fileData }
  }

  private fun PickerType.loadVisibleFiles(
    context: Context,
  ): List<FileDataUI> {
    var files: List<FileDataUI>

    val time = measureTime {
      files = uri()
        ?.cursor(context = context, fileType = this)
        ?.loadVisibleFiles(constructor = constructor())
        ?: emptyList()
    }

    Logger.d(TAG, "loadVisibleFiles: fileType = $this")
    Logger.d(TAG, "loadVisibleFiles: files = ${files.size}")
    Logger.d(TAG, "loadVisibleFiles: time = $time")

    return files
  }

  private fun PickerType.loadVisibleFilesFormFolder(
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
            callBack = { callBack(files + it) },
          )
      )
    }

    Logger.d(TAG, "loadVisibleFilesFormFolder: fileType = $this")
    Logger.d(TAG, "loadVisibleFilesFormFolder: folder = $folder")
    Logger.d(TAG, "loadVisibleFilesFormFolder: files = ${files.size}")
    Logger.d(TAG, "loadVisibleFilesFormFolder: time = $time")

    callBack(files)

    return files
  }

  private fun PickerType.uri(): Uri? {
    return when (this) {
      PickerType.Photo -> when (Build.VERSION.SDK_INT >= 29) {
        true -> MediaStore.Images.Media.getContentUri("external_primary")
        else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
      }

      PickerType.Video -> when (Build.VERSION.SDK_INT >= 29) {
        true -> MediaStore.Video.Media.getContentUri("external_primary")
        else -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
      }

      else -> null
    }
  }

  private fun PickerType.constructor(): KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> {
    return when (this) {
      PickerType.Photo -> ::PhotoModelUI
      PickerType.Video -> ::VideoModelUI
      PickerType.File -> ::FileModelUI
      PickerType.Trash -> ::TrashModelUI
      PickerType.Note -> ::TrashModelUI
    }
  }

  private fun Uri.cursor(
    context: Context,
    fileType: PickerType,
  ): Cursor? {
    val projection = FileData.entries.toMutableList()

    if (fileType != PickerType.Video) {
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
          name.checkName(),
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
    fileType: PickerType,
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> = fileType.constructor(),
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
            /* name = */ file.name.checkName(),
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

  private fun String.checkName(): String {
    val reservedChars = "?:\"*|/\\<>\u0000"

    return filter { char -> reservedChars.indexOf(char) == -1 }
      .filterIndexed { index, _ -> index < 20 }
      .replace("\n", "")
      .trimIndent()
      .trim()
  }

  private suspend fun List<FileDataUI>.toInvisibleFiles(
    fileType: PickerType,
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> = fileType.constructor(),
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    delay(10)

    val list = mutableListOf<FileDataUI>()

    forEach { file ->
      try {
        val source = File(file.path)
        val target = File(documents, "$hiddenDirectory/${fileType.name}/(${file.dateHidden})${source.name}")

        move(
          source = source,
          target = target,
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
        Logger.e(TAG, "hideFiles: $e")
      }
    }

    list.addToFilesData(fileType)

    callBack(list)
  }

  private suspend fun List<FileDataUI>.toVisibleFiles(
    fileType: PickerType,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
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

          list.add(file)
        }
      } catch (e: Exception) {
        Logger.e(TAG, "moveToVisible: $e")
      }
    }

    list.removeFromFileData(fileType = fileType)

    callBack(list)
  }

  private suspend fun List<FileDataUI>.toDeletedFiles(
    fileType: PickerType,
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> = fileType.constructor(),
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    if (fileType == PickerType.Note) {
      callBack(this)
      return
    }

    delay(10)

    val list = mutableListOf<FileDataUI>()

    File(documents, "$hiddenDirectory/${PickerType.Trash.name}").create()

    forEach { file ->
      try {
        file.hiddenPath?.let { hiddenPath ->
          val source = File(hiddenPath)

          val target = File(
            hiddenPath.replace(
              "$hiddenDirectory/${fileType.name}",
              "$hiddenDirectory/${PickerType.Trash.name}",
            )
          )

          move(
            source = source,
            target = target,
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
        }
      } catch (e: Exception) {
        Logger.e(TAG, "moveToDeletedFiles: $e")
      }
    }

    list.addToFilesData(fileType = PickerType.Trash)
    list.removeFromFileData(fileType = fileType)

    callBack(list)
  }

  private suspend fun List<TrashModelUI>.toInvisibleFiles(
    callBack: suspend (List<TrashModelUI>) -> Unit,
  ) {
    delay(10)

    val list = mutableListOf<TrashModelUI>()

    forEach { file ->
      try {
        file.hiddenPath?.let { hiddenPath ->
          val source = File(hiddenPath)

          val target = File(
            hiddenPath.replace(
              "$hiddenDirectory/${PickerType.Trash.name}",
              "$hiddenDirectory/${file.fileType.name}",
            )
          )

          move(
            source = source,
            target = target,
          )

          val newFile = file.copy(
            hiddenPath = target.absolutePath,
          )

          newFile.fileType = file.fileType
          newFile.duration = file.duration

          list.add(newFile)
        }
      } catch (e: Exception) {
        Logger.e(TAG, "restore: $e")
      }
    }

    list
      .groupBy { it.fileType }
      .forEach { (key, value) ->
        value.addToFilesData(fileType = key)
      }

    list.removeFromFileData(fileType = PickerType.Trash)

    callBack(list)
  }

  private suspend fun List<FileDataUI>.delete(
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    delay(10)

    forEach {
      try {
        File(it.hiddenPath ?: it.path).delete()
      } catch (_: Exception) {
      }
    }

    callBack(this)
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

  private fun fileDataFile(fileType: PickerType): File {
    val filesData = File(documents, "$hiddenDirectory/${fileType.name}/$fileData")
    if (!filesData.exists()) filesData.createNewFile()

    return filesData
  }

  private fun File.fileDataJson(): JSONObject {
    val jsonString = readText().ifEmpty { "{}" }
    return JSONObject(jsonString)
  }

  private fun List<FileDataUI>.fileDataJson(): JSONObject {
    val jsonString = Gson().toJson(associate { it.path to it.hiddenPath })
    return JSONObject(jsonString)
  }

  private fun List<FileDataUI>.addToFilesData(
    fileType: PickerType,
  ) {
    val filesData = fileDataFile(fileType)

    val jsonObjectNew = fileDataJson()
    val jsonObjectOld = filesData.fileDataJson()

    for (key in jsonObjectNew.keys()) {
      jsonObjectOld.put(key, jsonObjectNew.get(key))
    }

    filesData.writeText(jsonObjectOld.toString().replace("\\", ""))
  }

  private fun List<FileDataUI>.removeFromFileData(
    fileType: PickerType,
  ) {
    val filesData = fileDataFile(fileType)

    val jsonObjectNew = fileDataJson()
    val jsonObjectOld = filesData.fileDataJson()

    for (key in jsonObjectNew.keys()) {
      jsonObjectOld.remove(key)
    }

    filesData.writeText(jsonObjectOld.toString().replace("\\", ""))
  }

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

  private fun String.getMediaDuration(): String {
    var millis: Long = 0

    try {
      MediaMetadataRetriever().apply {
        setDataSource(this@getMediaDuration)

        val time: String? = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        millis = time?.toLong() ?: 0
      }
    } catch (ignore: Exception) {
    }

    return millis.getMediaDurationString()
  }
}