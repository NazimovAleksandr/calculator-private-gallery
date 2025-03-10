package com.next.level.solutions.calculator.fb.mp.entity.db

import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI

interface FileDataDB {
  fun toUI(): FileDataUI
}