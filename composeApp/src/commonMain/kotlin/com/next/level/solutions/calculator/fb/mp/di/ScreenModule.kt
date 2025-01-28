package com.next.level.solutions.calculator.fb.mp.di

import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.getMathParser
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.getLanguageChanger
import org.koin.dsl.module

val screenModule = module {
  single<LanguageChanger> { getLanguageChanger() }
  factory<MathParser> { getMathParser() }
}