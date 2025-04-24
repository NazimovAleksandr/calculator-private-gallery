package com.next.level.solutions.calculator.fb.mp.ui.screen.offer.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.and
import calculator_privategallery.composeapp.generated.resources.privacy_policy
import calculator_privategallery.composeapp.generated.resources.tos
import com.next.level.solutions.calculator.fb.mp.ui.screen.offer.OfferComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrivacyTos(
  component: OfferComponent?,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
  ) {
    val text = buildAnnotatedString {
      val privacyPolicy = stringResource(resource = Res.string.privacy_policy)

      withLink(
        LinkAnnotation.Clickable(
          tag = privacyPolicy,
          styles = TextLinkStyles(
            style = SpanStyle(
              textDecoration = TextDecoration.Underline,
              fontSize = 15.sp,
              fontWeight = FontWeight(400),
            )
          ),
          linkInteractionListener = {
            component?.action(OfferComponent.Action.Privacy)
          },
        )
      ) {
        append(text = privacyPolicy)
      }

      append(text = " ")
      append(text = stringResource(resource = Res.string.and))
      append(text = " ")

      val tos = stringResource(resource = Res.string.tos)

      withLink(
        LinkAnnotation.Clickable(
          tag = tos,
          styles = TextLinkStyles(
            style = SpanStyle(
              textDecoration = TextDecoration.Underline,
              fontSize = 15.sp,
              fontWeight = FontWeight(400),
            )
          ),
          linkInteractionListener = {
            component?.action(OfferComponent.Action.Tos)
          },
        )
      ) {
        append(text = tos)
      }
    }

    BasicText(
      text = text,
      style = TextStyleFactory.FS14.w600(),
      modifier = Modifier
        .padding(top = 8.dp)
    )
  }
}