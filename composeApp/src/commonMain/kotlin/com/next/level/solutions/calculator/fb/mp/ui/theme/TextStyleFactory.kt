package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

object TextStyleFactory {
    abstract class Factory {
        abstract val fontSizeDef: Int
        abstract val lineHeightDef: Int

        private val textAlignDef: TextAlign = TextAlign.Start

        private val colorDef: Color
            @Composable
            get() = MaterialTheme.colors.onBackground

        private fun createTextStyle(
            fontSize: Int,
            lineHeight: Int,
            fontWeight: FontWeight,
            textAlign: TextAlign,
            color: Color,
        ): TextStyle = TextStyle(
            fontSize = fontSize.sp,
            lineHeight = lineHeight.sp,
            fontWeight = fontWeight,
            textAlign = textAlign,
            color = color,
        )

        @Composable
        fun w300(
            lineHeight: Int = lineHeightDef,
            textAlign: TextAlign = textAlignDef,
            color: Color = colorDef,
        ): TextStyle = createTextStyle(
            fontSize = fontSizeDef,
            lineHeight = lineHeight,
            fontWeight = FontWeight.W300,
            textAlign = textAlign,
            color = color,
        )

        @Composable
        fun w400(
            lineHeight: Int = lineHeightDef,
            textAlign: TextAlign = textAlignDef,
            color: Color = colorDef,
        ): TextStyle = createTextStyle(
            fontSize = fontSizeDef,
            lineHeight = lineHeight,
            fontWeight = FontWeight.W400,
            textAlign = textAlign,
            color = color,
        )

        @Composable
        fun w500(
            lineHeight: Int = lineHeightDef,
            textAlign: TextAlign = textAlignDef,
            color: Color = colorDef,
        ): TextStyle = createTextStyle(
            fontSize = fontSizeDef,
            lineHeight = lineHeight,
            fontWeight = FontWeight.W500,
            textAlign = textAlign,
            color = color,
        )

        @Composable
        fun w600(
            lineHeight: Int = lineHeightDef,
            textAlign: TextAlign = textAlignDef,
            color: Color = colorDef,
        ): TextStyle = createTextStyle(
            fontSize = fontSizeDef,
            lineHeight = lineHeight,
            fontWeight = FontWeight.W600,
            textAlign = textAlign,
            color = color,
        )

        @Composable
        fun w700(
            lineHeight: Int = lineHeightDef,
            textAlign: TextAlign = textAlignDef,
            color: Color = colorDef,
        ): TextStyle = createTextStyle(
            fontSize = fontSizeDef,
            lineHeight = lineHeight,
            fontWeight = FontWeight.W700,
            textAlign = textAlign,
            color = color,
        )

        @Composable
        fun w900(
            lineHeight: Int = lineHeightDef,
            textAlign: TextAlign = textAlignDef,
            color: Color = colorDef,
        ): TextStyle = createTextStyle(
            fontSize = fontSizeDef,
            lineHeight = lineHeight,
            fontWeight = FontWeight.W900,
            textAlign = textAlign,
            color = color,
        )
    }

    /**
     * @property fontSizeDef: Int = 10
     * @property lineHeightDef: Int = 12
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS10 : Factory() {
        override val fontSizeDef: Int = 10
        override val lineHeightDef: Int = 12
    }

    /**
     * @property fontSizeDef: Int = 12
     * @property lineHeightDef: Int = 16
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS12 : Factory() {
        override val fontSizeDef: Int = 12
        override val lineHeightDef: Int = 16
    }

    /**
     * @property fontSizeDef: Int = 13
     * @property lineHeightDef: Int = 18
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS13 : Factory() {
        override val fontSizeDef: Int = 13
        override val lineHeightDef: Int = 18
    }

    /**
     * @property fontSizeDef: Int = 14
     * @property lineHeightDef: Int = 21
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS14 : Factory() {
        override val fontSizeDef: Int = 14
        override val lineHeightDef: Int = 21
    }

    /**
     * @property fontSizeDef: Int = 15
     * @property lineHeightDef: Int = 20
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS15 : Factory() {
        override val fontSizeDef: Int = 15
        override val lineHeightDef: Int = 20
    }

    /**
     * @property fontSizeDef: Int = 16
     * @property lineHeightDef: Int = 21
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS16 : Factory() {
        override val fontSizeDef: Int = 16
        override val lineHeightDef: Int = 21
    }

    /**
     * @property fontSizeDef: Int = 17
     * @property lineHeightDef: Int = 22
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS17 : Factory() {
        override val fontSizeDef: Int = 17
        override val lineHeightDef: Int = 22
    }

    /**
     * @property fontSizeDef: Int = 18
     * @property lineHeightDef: Int = 24
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS18 : Factory() {
        override val fontSizeDef: Int = 18
        override val lineHeightDef: Int = 24
    }

    /**
     * @property fontSizeDef: Int = 20
     * @property lineHeightDef: Int = 24
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS20 : Factory() {
        override val fontSizeDef: Int = 20
        override val lineHeightDef: Int = 24
    }

    /**
     * @property fontSizeDef: Int = 22
     * @property lineHeightDef: Int = 28
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS22 : Factory() {
        override val fontSizeDef: Int = 22
        override val lineHeightDef: Int = 28
    }

    /**
     * @property fontSizeDef: Int = 28
     * @property lineHeightDef: Int = 33
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS28 : Factory() {
        override val fontSizeDef: Int = 28
        override val lineHeightDef: Int = 33
    }

    /**
     * @property fontSizeDef: Int = 32
     * @property lineHeightDef: Int = 35
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS32 : Factory() {
        override val fontSizeDef: Int = 32
        override val lineHeightDef: Int = 35
    }

    /**
     * @property fontSizeDef: Int = 34
     * @property lineHeightDef: Int = 34
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS34 : Factory() {
        override val fontSizeDef: Int = 34
        override val lineHeightDef: Int = 34
    }

    /**
     * @property fontSizeDef: Int = 36
     * @property lineHeightDef: Int = 40
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS36 : Factory() {
        override val fontSizeDef: Int = 36
        override val lineHeightDef: Int = 40
    }

    /**
     * @property fontSizeDef: Int = 90
     * @property lineHeightDef: Int = 100
     * @property textAlignDef: TextAlign = Start
     * @property colorDef: Color = onBackground
     */
    object FS90 : Factory() {
        override val fontSizeDef: Int = 90
        override val lineHeightDef: Int = 100
    }
}