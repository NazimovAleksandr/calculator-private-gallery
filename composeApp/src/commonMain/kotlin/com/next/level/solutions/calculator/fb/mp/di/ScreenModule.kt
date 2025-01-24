package com.next.level.solutions.calculator.fb.mp.di

import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.getLanguageChanger
import org.koin.dsl.module

val screenModule = module {
  single { getLanguageChanger() }
}