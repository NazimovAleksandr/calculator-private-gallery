package com.next.level.solutions.calculator.fb.mp.ui.screen.offer.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.next.level.solutions.calculator.fb.mp.ecosystem.billing.Product

@Composable
fun OfferCard(
  product: Product,
  modifier: Modifier = Modifier,
) {
  Row {
    Column {
      Text(
        text = "Offer Card",
      )


      Text(
        text = product.price,
      )
    }
  }
}