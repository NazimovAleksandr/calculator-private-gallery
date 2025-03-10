package com.next.level.solutions.calculator.fb.mp.data.database

import com.next.level.solutions.calculator.fb.mp.data.database.dao.BrowserHistoryDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.DaoDB
import com.next.level.solutions.calculator.fb.mp.data.database.dao.FileDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.NoteDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.PhotoDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.TrashDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.VideoDao
import com.next.level.solutions.calculator.fb.mp.entity._mapper.toDB
import com.next.level.solutions.calculator.fb.mp.entity._mapper.toUI
import com.next.level.solutions.calculator.fb.mp.entity.db.BrowserHistoryDB
import com.next.level.solutions.calculator.fb.mp.entity.db.FileDataDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.BrowserHistoryUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDatabase(
  database: MyDatabase,
) {
  private val browserHistoryDao: BrowserHistoryDao = database.browserHistoryDao()

  private val fileDao: FileDao = database.fileDao()
  private val noteDao: NoteDao = database.noteDao()
  private val photoDao: PhotoDao = database.photoDao()
  private val trashDao: TrashDao = database.trashDao()
  private val videoDao: VideoDao = database.videoDao()

  fun fetchByDate(type: PickerType): Flow<List<FileDataUI>> = type.dao().byDate().map(::fileDataUI)
  fun fetchBySize(type: PickerType): Flow<List<FileDataUI>> = type.dao().bySize().map(::fileDataUI)
  fun fetchByName(type: PickerType): Flow<List<FileDataUI>> = type.dao().byName().map(::fileDataUI)

  suspend fun insert(type: PickerType, files: List<FileDataUI>) {
    type.dao().insert(*files.map(type.insert()).toTypedArray())
  }

  suspend fun delete(type: PickerType, files: List<FileDataUI>) {
    type.dao().delete(*files.map(FileDataUI::toDB).toTypedArray())
  }

  fun fetchBrowserHistory(): Flow<List<BrowserHistoryUI>> {
    return browserHistoryDao.getItems().map { it.map(::toUI) }
  }

  suspend fun insert(item: BrowserHistoryUI) {
    val itemDB = browserHistoryDao.getItemByUrl(url = item.url)

    when (itemDB) {
      null -> browserHistoryDao.insert(item.toDB())

      else -> browserHistoryDao.update(
        itemDB.copy(
          time = item.time,
          title = item.title,
        )
      )
    }
  }

  suspend fun delete(vararg item: BrowserHistoryUI) {
    browserHistoryDao.delete(*item.map { it.toDB() }.toTypedArray())
  }

  private fun PickerType.dao(): DaoDB {
    return when (this) {
      PickerType.File -> fileDao
      PickerType.Note -> noteDao
      PickerType.Photo -> photoDao
      PickerType.Trash -> trashDao
      PickerType.Video -> videoDao
    }
  }

  private fun PickerType.isTrash(): Boolean = this == PickerType.Trash

  private fun PickerType.insert(): (FileDataUI) -> FileDataDB = when (isTrash()) {
    true -> FileDataUI::toTrashDB
    else -> FileDataUI::toDB
  }

  private fun fileDataUI(file: List<FileDataDB>): List<FileDataUI> = file.map(::toUI)
  private fun toUI(file: FileDataDB): FileDataUI = file.toUI()
  private fun toUI(file: BrowserHistoryDB): BrowserHistoryUI = file.toUI()
}