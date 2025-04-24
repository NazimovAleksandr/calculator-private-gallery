@file:Suppress("DEPRECATION")

package com.next.level.solutions.calculator.fb.mp.ecosystem.billing

import android.app.Activity
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.AcknowledgePurchaseResponseListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.FeatureType.PRODUCT_DETAILS
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingClient.SkuType
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.querySkuDetails
import com.next.level.solutions.calculator.fb.mp.data.datastore.local.store.LocalStore
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual class BillingManager(
  localStore: LocalStore,
  private val activity: Activity,
  private val networkManager: NetworkManager,
) {
  private var billingClient: BillingClient = BillingClient
    .newBuilder(activity)
    .setListener(purchasesUpdatedListener())
    .enablePendingPurchases()
    .build()

  private val _bought: MutableStateFlow<Boolean> = MutableStateFlow(localStore.bought)
  actual val bought: Flow<Boolean> get() = _bought.asStateFlow()

  private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
  actual val products: Flow<List<Product>> get() = _products.asStateFlow()

  private val productDetailsList: MutableList<ProductDetails> = mutableListOf()
  private val skuDetailsList: MutableList<SkuDetails> = mutableListOf()

  actual fun init() {
    networkManager.runAfterNetworkConnection {
      billingClient.startConnection(billingClientStateListener())
    }
  }

  actual fun buy(product: Product) {
    when (isFeatureNotSupported()) {
      true -> buySku(product.type.id)
      else -> buyProduct(product.type.id)
    }
  }

  fun queryPurchasesAsync() {
    when (isFeatureNotSupported()) {
      true -> queryPurchasesAsyncSku()
      else -> queryPurchasesAsyncProduct()
    }
  }

  private fun purchasesUpdatedListener(): PurchasesUpdatedListener {
    return PurchasesUpdatedListener { billingResult, purchases ->
      checkPurchases(billingResult, purchases)
    }
  }

  private fun billingClientStateListener(): BillingClientStateListener {
    return object : BillingClientStateListener {
      var disconnectedCount = 0

      override fun onBillingServiceDisconnected() {
        if (disconnectedCount <= 10) {
          billingClient.startConnection(this)
          disconnectedCount++
        }
      }

      override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingResponseCode.OK) {
          CoroutineScope(Dispatchers.Default).launch {
            queryPurchasesAsync()
            queryDetails()
          }
        }
      }
    }
  }

  private fun isFeatureNotSupported(): Boolean {
    val supported = billingClient.isFeatureSupported(PRODUCT_DETAILS).responseCode
    return supported == BillingResponseCode.FEATURE_NOT_SUPPORTED
  }

  private suspend fun queryDetails() {
    when (isFeatureNotSupported()) {
      true -> querySkuDetails()
      else -> queryProductDetails()
    }
  }

  private suspend fun querySkuDetails() {
    val skuList = listOf(
      Products.OneYear.id,
      Products.ThreeMonths.id,
      Products.OneMonth.id,
    )

    val params: SkuDetailsParams = SkuDetailsParams.newBuilder()
      .setType(SkuType.SUBS)
      .setSkusList(skuList)
      .build()

    val skuDetailsResult: List<SkuDetails> = withContext(Dispatchers.IO) {
      billingClient.querySkuDetails(params).skuDetailsList ?: emptyList()
    }

    skuDetailsList.clear()
    skuDetailsList.addAll(skuDetailsResult)

    _products.value = skuDetailsResult.map {
      Product(price = it.price, type = it.sku.toProductType())
    }
  }

  private suspend fun queryProductDetails() {
    val productList: List<QueryProductDetailsParams.Product> = listOf(
      Products.OneYear.id.toProductSUBS(),
      Products.ThreeMonths.id.toProductSUBS(),
      Products.OneMonth.id.toProductSUBS(),
    )

    val productQuery: QueryProductDetailsParams = QueryProductDetailsParams
      .newBuilder()
      .setProductList(productList)
      .build()

    val productDetailsResult: List<ProductDetails> = withContext(Dispatchers.IO) {
      billingClient.queryProductDetails(productQuery).productDetailsList ?: emptyList()
    }

    productDetailsList.clear()
    productDetailsList.addAll(productDetailsResult)

    _products.value = productDetailsResult.map {
      Product(price = it.getPrice(), type = it.productId.toProductType())
    }
  }

  private fun String.toProductType(): Products {
    return when (this) {
      Products.OneYear.id -> Products.OneYear
      Products.ThreeMonths.id -> Products.ThreeMonths
      Products.OneMonth.id -> Products.OneMonth
      else -> Products.OneYear
    }
  }

  private fun String.toProductSUBS(): QueryProductDetailsParams.Product {
    return QueryProductDetailsParams.Product.newBuilder()
      .setProductId(this)
      .setProductType(ProductType.SUBS)
      .build()
  }

  private fun ProductDetails.getPrice(): String {
    return subscriptionOfferDetails?.get(0)?.pricingPhases?.pricingPhaseList?.get(0)?.formattedPrice.toString()
  }

  private fun queryPurchasesAsyncSku() {
    billingClient.queryPurchasesAsync(SkuType.SUBS) { billingResult, purchases ->
      checkPurchases(billingResult, purchases)
    }
  }

  private fun queryPurchasesAsyncProduct() {
    val purchasesParams = QueryPurchasesParams.newBuilder()
      .setProductType(ProductType.SUBS)
      .build()

    billingClient.queryPurchasesAsync(purchasesParams) { billingResult, purchases ->
      checkPurchases(billingResult, purchases)
    }
  }

  private fun checkPurchases(
    billingResult: BillingResult,
    purchases: MutableList<Purchase>?
  ) {
    if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
      for (purchase in purchases) {
        CoroutineScope(Dispatchers.IO).launch {
          handlePurchase(purchase)
        }
      }
    }
  }

  private suspend fun handlePurchase(purchase: Purchase) {
    when (purchase.purchaseState) {
      Purchase.PurchaseState.PENDING -> _bought.value = false
      Purchase.PurchaseState.UNSPECIFIED_STATE -> _bought.value = false
      Purchase.PurchaseState.PURCHASED -> statePurchased(purchase)
    }
  }

  private suspend fun statePurchased(purchase: Purchase) {
    when (purchase.isAcknowledged) {
      true -> _bought.value = purchase.isAutoRenewing

      else -> withContext(Dispatchers.IO) {
        billingClient.acknowledgePurchase(
          purchaseParams(purchase),
          acknowledgePurchaseListener(),
        )
      }
    }
  }

  private fun purchaseParams(purchase: Purchase): AcknowledgePurchaseParams {
    return AcknowledgePurchaseParams
      .newBuilder()
      .setPurchaseToken(purchase.purchaseToken)
      .build()
  }

  private fun acknowledgePurchaseListener(): AcknowledgePurchaseResponseListener {
    return AcknowledgePurchaseResponseListener { billingResult ->
      if (billingResult.responseCode == BillingResponseCode.OK) {
        _bought.value = true
      }
    }
  }

  private fun buySku(id: String) {
    val skuDetails: SkuDetails = skuDetailsList.find { it.sku == id } ?: return

    val flowParams = BillingFlowParams.newBuilder()
      .setSkuDetails(skuDetails)
      .build()

    billingClient.launchBillingFlow(activity, flowParams)
  }

  private fun buyProduct(id: String) {
    val productDetails: ProductDetails = productDetailsList.find { it.productId == id } ?: return
    val offerToken = productDetails.subscriptionOfferDetails?.get(0)?.offerToken ?: return

    val productDetailsParamsList = listOf(
      BillingFlowParams.ProductDetailsParams.newBuilder()
        .setProductDetails(productDetails)
        .setOfferToken(offerToken)
        .build()
    )

    val billingFlowParams = BillingFlowParams.newBuilder()
      .setProductDetailsParamsList(productDetailsParamsList)
      .build()

    billingClient.launchBillingFlow(activity, billingFlowParams)
  }
}