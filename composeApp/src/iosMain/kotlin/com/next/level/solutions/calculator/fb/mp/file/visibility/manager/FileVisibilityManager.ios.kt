package com.next.level.solutions.calculator.fb.mp.file.visibility.manager

import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.VideoModelUI
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp.currentTimeMillis
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.CoreSpotlight.CSSearchableIndex
import platform.CoreSpotlight.CSSearchableItem
import platform.CoreSpotlight.CSSearchableItemAttributeSet
import platform.Foundation.NSArray
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSLocale
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSSortDescriptor
import platform.Foundation.NSTimeZone
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.defaultTimeZone
import platform.Photos.PHAsset
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAssetMediaTypeImage
import platform.Photos.PHAssetResource
import platform.Photos.PHFetchOptions
import platform.Photos.PHFetchResult
import platform.Photos.PHImageManager
import platform.Photos.PHImageRequestOptions
import platform.Photos.PHPhotoLibrary
import platform.UniformTypeIdentifiers.UTTypeItem
import platform.darwin.NSUInteger
import kotlin.reflect.KFunction8
import kotlin.time.measureTime

class FileVisibilityManagerImpl : FileVisibilityManager {
  companion object {
    private const val TAG = "TAG_FILE_HIDER"
    const val FILES_DATA_FILE_NAME = "filesData.txt"
    private const val HIDDEN_DIRECTORY = ".HiddenDirectory_Calculator"
  }

  private val dateFormat: NSDateFormatter = NSDateFormatter().apply {
    dateFormat = "HH_mm_ss_SSS"
    timeZone = NSTimeZone.defaultTimeZone
    locale = NSLocale.currentLocale
  }

  private val dateHidden: String
    get() = dateFormat.stringFromDate(
      date = NSDate.dateWithTimeIntervalSince1970(currentTimeMillis() / 1000.0),
    )

  override fun invisibleFiles(
    fileType: PickerType,
  ): List<FileDataUI> {
    NSFileManager.defaultManager.createHiddenDirectory(fileType)
    return fileType.loadHiddenFiles()
  }

  override fun visibleFiles(
    fileType: PickerType,
    viewType: PickerMode,
  ): List<FileDataUI> {
    return when (viewType) {
      PickerMode.Gallery -> fileType.loadVisibleFiles()

//      FilePickerViewType.Folder -> fileType //todo
//        .loadVisibleFilesFormFolder(
//          folder = externalStorage,
//        )

      else -> fileType.loadVisibleFiles()
    }
  }

  override fun visibleFiles(
    folder: String?,
    fromParent: Boolean,
    fileType: PickerType,
    callBack: (List<FileDataUI>) -> Unit,
  ) {
    // TODO("Not yet implemented")
  }

  override suspend fun moveToInvisibleFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    return files.hide(
      fileType = fileType,
      callBack = callBack,
    )
  }

  override suspend fun moveToVisibleFiles(
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    files.forEach {
      when (it) {
        is PhotoModelUI -> it.restore()
        is VideoModelUI -> {} // todo
        is FileModelUI -> {} // todo
        is TrashModelUI -> {} // todo
        else -> {}
      }
    }

    callBack(files)
  }

  override suspend fun moveToDeletedFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    // TODO("Not yet implemented")
  }

  override suspend fun restoreToInvisibleFiles(
    files: List<TrashModelUI>,
    callBack: suspend (List<TrashModelUI>) -> Unit,
  ) {
    // TODO("Not yet implemented")
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

  private fun PickerType.loadHiddenFiles(): List<FileDataUI> {
    var files: List<FileDataUI>

    val time = measureTime {
      files = loadFiles()
    }

    Logger.d(TAG, "loadHiddenFiles: files = ${files.size}, time = $time")

    return files
  }

  @OptIn(ExperimentalForeignApi::class)
  private fun PickerType.loadFiles(
    constructor: KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> = constructor()
  ): List<FileDataUI> {
    val documentDirectory = NSSearchPathForDirectoriesInDomains(
      directory = NSDocumentDirectory,
      domainMask = NSUserDomainMask,
      expandTilde = true,
    ).firstOrNull() as? String ?: return emptyList()

    val hiddenPath = "$documentDirectory/$HIDDEN_DIRECTORY/${name}"

    val files = NSFileManager.defaultManager.contentsOfDirectoryAtPath(hiddenPath, null)

    val fullPaths = files
      ?.map { "$hiddenPath/$it" }
      ?.map { path ->
        Logger.d(TAG, "loadFiles: $path")

        constructor.invoke(
          /* path = */  path,
          /* name = */ path.substringAfterLast("/"),
          /* folder = */  "",
          /* size = */  0L,
          /* dateAdded = */  "",
          /* dateHidden = */  "",
          /* dateModified = */  "",
          /* hiddenPath = */  null,
        ).apply {
          when {
            this is PhotoModelUI -> url = NSURL.fileURLWithPath(path = path)

//            PickerType.Video -> emptyList() // todo
//            PickerType.File -> emptyList() // todo
//            PickerType.Trash -> emptyList() // todo
//            PickerType.Note -> emptyList() // todo
          }
        }
      }

    return fullPaths ?: emptyList()
  }

  private suspend fun List<FileDataUI>.hide(
    fileType: PickerType,
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    when (fileType) {
      PickerType.Photo -> hidePhotos(callBack)

      PickerType.Video -> emptyList<String>() // todo
      PickerType.File -> emptyList<String>() // todo
      PickerType.Trash -> emptyList<String>() // todo
      PickerType.Note -> emptyList<String>() // todo
    }

//    val list = mutableListOf<FileDataUI>()
//
//    forEach { file ->
//      try {
//        when (file) {
//          is PhotoModelUI -> file.hidde()
//          else -> {}
//        }
//
////        val source = File(file.path)
////        val target = File(documents, ".apps/${fileType.name}/(${file.dateHidden})${source.name}")
////
////        move(
////          source = source,
////          target = target,
////        )
//
////        addToFilesData(
////          fileType = fileType,
////          sourcePath = source.absolutePath,
////          targetPath = target.absolutePath,
////        )
//
//        val newFile = constructor.invoke(
//          /*path =*/ file.path,
//          /*name =*/ file.name,
//          /*folder =*/ file.folder,
//          /*size =*/ file.size,
//          /*dateAdded =*/ file.dateAdded,
//          /*dateHidden =*/ file.dateHidden,
//          /*dateModified =*/ file.dateModified,
//          /*hiddenPath =*/ file.hiddenPath,
//        )
//
//        if (newFile is VideoModelUI) {
//          newFile.duration = file.duration
//        }
//
//        list.add(newFile)
//      } catch (e: Exception) {
//        Logger.e(TAG, "hideFiles: $e")
//      }
//    }
//
//    return list
  }

  private suspend fun List<FileDataUI>.hidePhotos(
    callBack: suspend (List<FileDataUI>) -> Unit,
  ) {
    delay(10)

    val assets = filterIsInstance<PhotoModelUI>()
      .mapNotNull { it.asset as? PHAsset }

    removeItemsFromPhotoLibrary(assets) { success ->
      when {
        success -> {
          val hidden = filterIsInstance<PhotoModelUI>()
            .mapNotNull { file -> file.hidde() }
            .mapNotNull { file -> file.removeItemAtURL() }

          callBack(hidden)
        }

        else -> callBack(emptyList())
      }
    }
  }

  /*@OptIn(ExperimentalForeignApi::class)
  private fun getAllPhotos(onComplete: (List<FileDataUI>) -> Unit) {
    val photoList = mutableListOf<FileDataUI>()

    val fetchOptions = PHFetchOptions()
//    fetchOptions.sortDescriptors = listOf(NSSortDescriptor.key("creationDate", ascending = false))

//    PHAssetMediaTypeVideo
    val assets = PHAsset.fetchAssetsWithMediaType(PHAssetMediaTypeImage, fetchOptions)

    assets.enumerateObjectsUsingBlock { asset, _, _ ->
      val phAsset = asset as? PHAsset

      val assetId = phAsset?.localIdentifier // Уникальный ID фото

      val options = PHImageRequestOptions().apply {
        synchronous = true
      }

      PHImageManager.defaultManager().requestImageDataAndOrientationForAsset(
        asset = asset as PHAsset,
        options = options,
        resultHandler = { data, tt, pp, info ->
          Logger.d("Photo", "data = $data")
          Logger.d("Photo", "tt = $tt")
          Logger.d("Photo", "pp = $pp")
          Logger.d("Photo", "info = $info")
          val PHImageFileUTIKey = (info?.get("PHImageFileUTIKey"))
          Logger.d("Photo", "PHImageFileUTIKey = $PHImageFileUTIKey")

          val fileUrl = (info?.get("PHImageFileURLKey") as? NSURL)?.absoluteString
          Logger.d("Photo", "fileUrl = $fileUrl")
//          if (fileUrl.isNotEmpty()) {
//            photoList.add(FileDataUI(assetId, fileUrl))
//          }
        }
      )
    }

    onComplete(photoList)
  }*/

//  fun getAssetSize(asset: PHAsset): Long {
//    val resources = PHAssetResource.assetResourcesForAsset(asset)
//    var size: Long = 0
//    for (resource in resources) {
//      size += (resource).valueForKey("fileSize") as? Long ?: 0
//    }
//    return size
//  }

  /*private fun saveImageToTempFile(image: UIImage): NSURL? {
    val data = UIImagePNGRepresentation(image)
    val tempDir = NSFileManager.defaultManager.temporaryDirectory
    val tempFile = tempDir.URLByAppendingPathComponent("$image.png")

    return if (tempFile?.let { data?.writeToURL(it, atomically = true) } == true) {
      tempFile
    } else {
      null
    }
  }*/

//  @OptIn(ExperimentalForeignApi::class)
//  private fun saveToHiddenFolder(sourceUrl: NSURL, onComplete: (Boolean) -> Unit) {
//    val documentDirectory = NSSearchPathForDirectoriesInDomains(
//      directory = NSDocumentDirectory,
//      domainMask = NSUserDomainMask,
//      expandTilde = true,
//    ).firstOrNull()
//
//    val hiddenPath = "$documentDirectory/.hidden_photos"
//
//    val fileManager = NSFileManager.defaultManager
//
//    if (!fileManager.fileExistsAtPath(hiddenPath)) {
//      fileManager.createDirectoryAtPath(
//        path = hiddenPath,
//        withIntermediateDirectories = true,
//        attributes = null,
//        error = null
//      )
//    }
//
//    val destinationUrl = NSURL.fileURLWithPath("$hiddenPath/${sourceUrl.lastPathComponent}")
//    val success = fileManager.copyItemAtURL(sourceUrl, destinationUrl, null)
//
//    onComplete(success)
//  }

  private fun deletePhoto(assetId: String, onComplete: (Boolean) -> Unit) {
    PHPhotoLibrary
      .sharedPhotoLibrary()
      .performChanges(
        {
          val asset = PHAsset.fetchAssetsWithLocalIdentifiers(
            identifiers = listOf(assetId),
            options = null,
          ).firstObject() as? PHAsset

          Logger.d(TAG, "(assetId) asset = $asset")

          asset?.let {
//            PHAssetChangeRequest.deleteAssets(listOf(it))
          }
        }
      ) { success, error ->
        onComplete(success)
      }
  }

  private fun deletePhoto(url: NSURL, onComplete: (Boolean) -> Unit) {
    PHPhotoLibrary
      .sharedPhotoLibrary()
      .performChanges(
        {
          val asset = PHAsset.fetchAssetsWithALAssetURLs(
            assetURLs = listOf(url),
            options = null,
          ).firstObject() as? PHAsset

          Logger.d(TAG, "(NSURL) asset = $asset")

          asset?.let {
//            PHAssetChangeRequest.deleteAssets(listOf(it))
          }
        }
      ) { success, error ->
        onComplete(success)
      }
  }

  @OptIn(ExperimentalForeignApi::class)
  private fun PhotoModelUI.restore() {
    val srcURL: NSURL? = url as? NSURL
    val toURL: NSURL? = NSURL.URLWithString(url.toString().replace("($dateHidden)", ""))

    srcURL ?: return
    toURL ?: return

    NSFileManager.defaultManager.copyItemAtURL(
      srcURL = srcURL,
      toURL = toURL,
      error = null,
    )

    toURL.restorePhotoFromHiddenFolder {
      removeItemAtURL()
    }
  }


  // todo working ------------------------------------------

  private fun NSFileManager.createHiddenDirectory(fileType: PickerType) {
    val hiddenDirectory: NSURL? = hiddenDirectory()

    if (!fileExistsAtPath(hiddenDirectory?.path!!)) {
      @OptIn(ExperimentalForeignApi::class)
      createDirectoryAtURL(
        hiddenDirectory,
        withIntermediateDirectories = true,
        attributes = null,
        error = null
      )
    }

    val fileTypeDirectory: NSURL? = hiddenDirectory.URLByAppendingPathComponent(fileType.name)

    if (!fileExistsAtPath(fileTypeDirectory?.path!!)) {
      @OptIn(ExperimentalForeignApi::class)
      createDirectoryAtURL(
        fileTypeDirectory,
        withIntermediateDirectories = true,
        attributes = null,
        error = null
      )
    }
  }

  private fun NSFileManager.hiddenDirectory(): NSURL? {
    val documentsDirectory: NSURL? = URLsForDirectory(
      directory = NSDocumentDirectory,
      inDomains = NSUserDomainMask,
    ).firstOrNull() as? NSURL

    return documentsDirectory?.URLByAppendingPathComponent(
      pathComponent = HIDDEN_DIRECTORY,
    )
  }

  private fun PickerType.loadVisibleFiles(): List<FileDataUI> {
    var files: List<FileDataUI>

    val time = measureTime {
      files = when (this) {
        PickerType.Photo -> loadVisiblePhotos()
        else -> emptyList()
      }
    }

    Logger.d(TAG, "loadVisibleFiles: files = ${files.size}")
    Logger.d(TAG, "loadVisibleFiles: time = $time")

    return files
  }

  private fun loadVisiblePhotos(): List<PhotoModelUI> {
    val images = mutableListOf<PhotoModelUI>()

    // Настройка запроса для получения изображений
    val fetchOptions = PHFetchOptions().apply {
      sortDescriptors = listOf(NSSortDescriptor(key = "creationDate", ascending = false))
    }

    // Получение всех изображений из библиотеки
    val fetchResult: PHFetchResult = PHAsset.fetchAssetsWithMediaType(
      mediaType = PHAssetMediaTypeImage,
      options = fetchOptions
    )

    // Менеджер для загрузки изображений
    val imageManager = PHImageManager.defaultManager()

    // Параметры запроса изображения
    val requestOptions = PHImageRequestOptions().apply {
//      version = PHImageRequestOptionsVersionCurrent
      synchronous = false
    }

    for (i in ULong.MIN_VALUE until fetchResult.count) {
      val asset: PHAsset = fetchResult.objectAtIndex(i) as PHAsset
      var size: NSUInteger? = null

      imageManager.requestImageDataForAsset(
        asset = asset,
        options = requestOptions,
        resultHandler = { data, _, _, _ -> size = data?.length }
      )

      images.add(
        PhotoModelUI(
          path = asset.toString(),
          name = getImageFileName(asset) ?: asset.creationDate.toString(),
          folder = "TODO()",
          size = size?.toLong() ?: 123,
          dateAdded = asset.creationDate.toString(),
          dateHidden = dateHidden,
          dateModified = asset.creationDate.toString(),
        ).also { file ->
          file.asset = asset
        }
      )
    }

    return images
  }

  private fun getImageFileName(asset: PHAsset): String? {
    val resources: PHAssetResource? = PHAssetResource
      .assetResourcesForAsset(asset)
      .firstOrNull() as? PHAssetResource

    return resources?.originalFilename
  }

  private fun PhotoModelUI.hidde(): PhotoModelUI? {
    url = url?.toString()?.let { NSURL.URLWithString(it) } ?: return null

    val fileManager: NSFileManager = NSFileManager.defaultManager

    val hiddenDirectory: NSURL = fileManager.hiddenDirectory() ?: return null

    val fileTypeDirectory: NSURL = hiddenDirectory.URLByAppendingPathComponent(
      pathComponent = fileType.name,
    ) ?: return null

    val toURL: NSURL = fileTypeDirectory.URLByAppendingPathComponent(
      pathComponent = "(${dateHidden})${name}",
    ) ?: return null

    @OptIn(ExperimentalForeignApi::class)
    val success = fileManager.copyItemAtURL(
      srcURL = url as NSURL,
      toURL = toURL,
      error = null,
    )

    Logger.d(TAG, "PhotoModelUI.hidde = $success")

    return when (success) {
      true -> this
      else -> null
    }
  }

  private fun Any.restorePhotoFromHiddenFolder(successCallback: () -> Unit) {
    if (this !is NSURL) return

    PHPhotoLibrary
      .sharedPhotoLibrary()
      .performChanges(
        changeBlock = { change() },
        completionHandler = { success, error ->
          restoreComplete(success, error)
          if (success) successCallback()
        },
      )
  }

  private fun NSURL.change() {
    PHAssetChangeRequest
      .creationRequestForAssetFromImageAtFileURL(fileURL = this)
//      ?.creationDate = NSDate() // todo need?
  }

  private fun restoreComplete(success: Boolean, error: NSError?) {
    when (success) {
      true -> Logger.d(TAG, "restoreComplete: Success")
      else -> Logger.e(TAG, "restoreComplete: Error: ${error?.localizedDescription}")
    }
  }

  @OptIn(ExperimentalForeignApi::class)
  private fun PhotoModelUI.removeItemAtURL(): PhotoModelUI? {
    return try {
      NSFileManager.defaultManager.removeItemAtURL(url as NSURL, null)
      val path: String = this.toString().replaceBefore("Documents", "")
      Logger.d(TAG, "Файл успешно удалён: $path")
      this
    } catch (error: Exception) {
      Logger.e(TAG, "Ошибка при удалении файла: ($error)")
      null
    }
  }

  private fun removeItemsFromPhotoLibrary(
    assets: List<PHAsset>,
    callback: suspend (success: Boolean) -> Unit,
  ) {
    PHPhotoLibrary
      .sharedPhotoLibrary()
      .performChanges({
        PHAssetChangeRequest.deleteAssets(assets as NSArray)
      }) { success, error ->
        CoroutineScope(Dispatchers.IO).launch {
          callback(success)
        }

        when (success) {
          true -> Logger.d(TAG, "PHAsset Файл успешно удалён: ")
          else -> Logger.e(TAG, "PHAsset Ошибка при удалении файла: ($error)")
        }
      }
  }

//  private fun reindexFiles(folderURL: NSURL) {
//    val index = CSSearchableIndex.defaultSearchableIndex()
//    val attributeSet = CSSearchableItemAttributeSet(contentType:.image)
//    attributeSet.title = "Фото"
//
//    val item = CSSearchableItem(folderURL.absoluteString, "photos", attributeSet)
//
//    index.indexSearchableItems(listOf(item)) { error ->
//      if (error != null) {
//        print("Ошибка индексации: $error")
//      } else {
//        print("Файлы успешно переиндексированы!")
//      }
//    }
//  }

  fun indexFile(fileURL: NSURL) {
    val attributeSet = CSSearchableItemAttributeSet(UTTypeItem.identifier)
//    attributeSet.title = title
//    attributeSet.contentDescription = description
//    attributeSet.url = fileURL

    val searchableItem = CSSearchableItem(
      uniqueIdentifier = fileURL.absoluteString,
      domainIdentifier = "Devices",
      attributeSet = attributeSet
    )

    CSSearchableIndex.defaultSearchableIndex()
      .indexSearchableItems(listOf(searchableItem)) { error ->
        if (error != null) {
          Logger.e(TAG, "Indexing failed: ${error.localizedDescription}")
        } else {
          Logger.d(TAG, "File indexed successfully!")
        }
      }
  }
}