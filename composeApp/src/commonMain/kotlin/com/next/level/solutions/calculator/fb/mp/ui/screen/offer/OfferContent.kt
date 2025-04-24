package com.next.level.solutions.calculator.fb.mp.ui.screen.offer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.settings
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.billing.Products
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Close
import com.next.level.solutions.calculator.fb.mp.ui.screen.offer.composable.OfferCard
import com.next.level.solutions.calculator.fb.mp.ui.screen.offer.composable.PrivacyTos
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OfferContent(
  component: OfferComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
internal fun OfferContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: OfferComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val selectedItem: Products by remember {
    derivedStateOf {
      model?.value?.selected ?: Products.OneYear
    }
  }

  val products = model?.value?.products?.collectAsState(
    initial = emptyList(),
  )

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
      .padding(horizontal = 24.dp)
  ) {
    Box {
      Text(
        text = stringResource(resource = Res.string.settings),
        style = TextStyleFactory.FS22.w600(),
        textAlign = TextAlign.Center,
        modifier = Modifier
          .padding(top = 12.dp)
      )

      Image(
        vector = MagicIcons.All.Close,
        colorFilter = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
          .size(40.dp)
          .clip(shape = CircleShape)
          .clickable(onClick = { component?.action(OfferComponent.Action.Back) })
          .padding(all = 8.dp)
      )
    }

    Spacer(modifier = Modifier.weight(weight = 1f))

    Products.entries.forEach { type ->
      val product = products?.value?.find { product -> product.type == type }

      product?.let { product ->
        OfferCard(
          product = product,
          modifier = Modifier
            .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(height = 8.dp))
      }
    }

    Spacer(modifier = Modifier.weight(weight = 1f))

    PrivacyTos(
      component = component,
    )

    Spacer(modifier = Modifier.weight(weight = 1f))

    ActionButton(
      text = stringResource(resource = Res.string.settings),
      modifier = Modifier
        .fillMaxWidth()
    ) {
      component?.action(OfferComponent.Action.Buy)
    }
  }
}