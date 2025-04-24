package com.next.level.solutions.calculator.fb.mp.ecosystem.billing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class BillingManager {
  actual val bought: Flow<Boolean> = flowOf(false)
  actual val products: Flow<List<Product>> = flowOf()

  actual fun init() {}

  actual fun buy(product: Product) {}
}