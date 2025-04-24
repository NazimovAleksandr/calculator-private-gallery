package com.next.level.solutions.calculator.fb.mp.ecosystem.billing

import kotlinx.coroutines.flow.Flow

expect class BillingManager {
  val bought: Flow<Boolean>
  val products: Flow<List<Product>>

  fun init()
  fun buy(product: Product)
}