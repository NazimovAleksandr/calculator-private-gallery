package com.next.level.solutions.calculator.fb.mp.data.database

import com.next.level.solutions.calculator.fb.mp.data.database.dao.BrowserHistoryDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.FileDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.NoteDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.PhotoDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.TrashDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.VideoDao
import com.next.level.solutions.calculator.fb.mp.entity._mapper.toDB
import com.next.level.solutions.calculator.fb.mp.entity._mapper.toTrashDB
import com.next.level.solutions.calculator.fb.mp.entity._mapper.toUI
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.BrowserHistoryUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.NoteModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.VideoModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class AppDatabase(
  val database: MyDatabase,
) {
  private val hiddenNoteDao: NoteDao = database.hiddenNoteDao()
  private val hiddenFileDao: FileDao = database.hiddenFileDao()
  private val hiddenPhotoDao: PhotoDao = database.hiddenPhotoDao()
  private val hiddenVideoDao: VideoDao = database.hiddenVideoDao()
  private val hiddenTrashDao: TrashDao = database.hiddenTrashDao()

  private val browserHistoryDao: BrowserHistoryDao = database.browserHistoryDao()

  fun getFormDatabaseByDateAdded(
    fileType: FilePickerFileType,
  ): Flow<List<FileDataUI>> {
    return when (fileType) {
      FilePickerFileType.Note -> hiddenNoteDao.getByDateAdded().map { it.map { model -> model.toUI() } }
      FilePickerFileType.File -> hiddenFileDao.getByDateAdded().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Photo -> hiddenPhotoDao.getByDateAdded().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Video -> hiddenVideoDao.getByDateAdded().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Trash -> hiddenTrashDao.getByDateAdded().map { it.map { model -> model.toUI() } }
    }
  }

  fun getFormDatabaseByFileSize(
    fileType: FilePickerFileType,
  ): Flow<List<FileDataUI>> {
    return when (fileType) {
      FilePickerFileType.Note -> hiddenNoteDao.getByFileSize().map { it.map { model -> model.toUI() } }
      FilePickerFileType.File -> hiddenFileDao.getByFileSize().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Photo -> hiddenPhotoDao.getByFileSize().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Video -> hiddenVideoDao.getByFileSize().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Trash -> hiddenTrashDao.getByFileSize().map { it.map { model -> model.toUI() } }
    }
  }

  fun getFormDatabaseByName(
    fileType: FilePickerFileType,
  ): Flow<List<FileDataUI>> {
    return when (fileType) {
      FilePickerFileType.Note -> hiddenNoteDao.getByName().map { it.map { model -> model.toUI() } }
      FilePickerFileType.File -> hiddenFileDao.getByName().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Photo -> hiddenPhotoDao.getByName().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Video -> hiddenVideoDao.getByName().map { it.map { model -> model.toUI() } }
      FilePickerFileType.Trash -> hiddenTrashDao.getByName().map { it.map { model -> model.toUI() } }
    }
  }

  suspend fun add(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ) {
    when (fileType) {
      FilePickerFileType.Note -> hiddenNoteDao.insert(*files.convert(NoteModelUI::toDB))
      FilePickerFileType.File -> hiddenFileDao.insert(*files.convert(FileModelUI::toDB))
      FilePickerFileType.Photo -> hiddenPhotoDao.insert(*files.convert(PhotoModelUI::toDB))
      FilePickerFileType.Video -> hiddenVideoDao.insert(*files.convert(VideoModelUI::toDB))
      FilePickerFileType.Trash -> hiddenTrashDao.insert(*files.convertToTrash())
    }
  }

  suspend fun deleteFromDatabase(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ) {
    when (fileType) {
      FilePickerFileType.Note -> files.forEach { hiddenNoteDao.delete(it.path) }
      FilePickerFileType.File -> hiddenFileDao.delete(*files.convert(FileModelUI::toDB))
      FilePickerFileType.Photo -> hiddenPhotoDao.delete(*files.convert(PhotoModelUI::toDB))
      FilePickerFileType.Video -> hiddenVideoDao.delete(*files.convert(VideoModelUI::toDB))
      else -> {}
    }
  }

  suspend fun moveToTrashDB(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ) {
    when (fileType) {
      FilePickerFileType.Trash -> {
        files.forEach {
          hiddenTrashDao.delete(it.path)
        }
      }

      FilePickerFileType.File -> {
        hiddenFileDao.delete(*files.convert(FileModelUI::toDB))
      }

      else -> {
        val trashDBS = when (fileType) {
          FilePickerFileType.Photo -> files.convert(PhotoModelUI::toTrashDB)
          FilePickerFileType.Video -> files.convert(VideoModelUI::toTrashDB)
          else -> arrayOf()
        }

        hiddenTrashDao.insert(*trashDBS)

        deleteFromDatabase(
          fileType = fileType,
          files = files,
        )
      }
    }
  }

  suspend fun restoreFiles(
    files: List<FileDataUI>,
  ) {
    files.forEach { file ->
      when (file) {
        is FileModelUI -> hiddenFileDao.insert(file.toDB())
        is PhotoModelUI -> hiddenPhotoDao.insert(file.toDB())
        is VideoModelUI -> hiddenVideoDao.insert(file.toDB())
        else -> {}
      }

      hiddenTrashDao.delete(file.path)
    }
  }

  suspend fun getNotes(
    date: String,
  ): NoteModelUI {
    return hiddenNoteDao.get(
      dateAdded = date,
    ).toUI()
  }

  fun getBrowserHistory(): Flow<List<BrowserHistoryUI>> {
    return browserHistoryDao.getItems().map { list ->
      list.map { it.toUI() }
    }
  }

  suspend fun getBrowserHistoryItemById(id: Int): BrowserHistoryUI? {
    return browserHistoryDao.getItemById(id)?.toUI()
  }

  suspend fun getBrowserHistoryItemByUrl(url: String): BrowserHistoryUI? {
    return browserHistoryDao.getItemByUrl(url)?.toUI()
  }

  /*suspend fun getLastItemByAd(): BrowserHistoryUI? {
      return browserHistoryDao.getLastItemByAd()?.toUI()
  }*/

  suspend fun browserHistoryIsEmpty(): Boolean {
    return getBrowserHistory().first().isEmpty()
  }

  suspend fun addBrowserHistory(item: BrowserHistoryUI) {
    browserHistoryDao.getItemByUrl(url = item.url).let { itemDB ->
      when (itemDB) {
        null -> browserHistoryDao.insert(item.toDB())

        else -> browserHistoryDao.update(
          itemDB.copy(
            time = item.time,
            title = item.title,
            isRemove = false
          )
        )
      }
    }
  }

  suspend fun deleteBrowserHistoryItem(item: BrowserHistoryUI) {
    item.id?.let { browserHistoryDao.deleteItemById(it) }
  }

  suspend fun deleteAllBrowserHistory() {
    browserHistoryDao.deleteAll()
  }

  private inline fun <T, reified R> List<FileDataUI>.convert(
    toDB: T.() -> R,
  ): Array<R> {
    return map {
      @Suppress("UNCHECKED_CAST")
      (it as T).toDB()
    }.toTypedArray()
  }

  private fun List<FileDataUI>.convertToTrash(): Array<TrashModelDB> {
    return mapNotNull {
      when (it) {
        is PhotoModelUI -> it.toTrashDB()
        is VideoModelUI -> it.toTrashDB()
        else -> null
      }
    }.toTypedArray()
  }
}