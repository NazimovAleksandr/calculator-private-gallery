package com.next.level.solutions.calculator.fb.mp.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Photos.PHAsset
import platform.Photos.PHContentEditingInputRequestOptions
import platform.Photos.requestContentEditingInputWithOptions

@OptIn(ExperimentalForeignApi::class)
actual fun getImageURL(asset: Any?, callback: (String) -> Unit) {
  val options = PHContentEditingInputRequestOptions()
  (asset as? PHAsset)?.requestContentEditingInputWithOptions(options) { input, _ ->
//    Logger.d("first", """
//      -
//      asset: $asset
//      fileURL: ${input?.fullSizeImageURL}
//      -
//    """.trimIndent())
    callback(input?.fullSizeImageURL.toString())
  }

//  PHImageManager.defaultManager().requestImageDataAndOrientationForAsset(
//    asset = asset as PHAsset,
//    options = PHImageRequestOptions().apply {
//      synchronous = false
//    }
//  ) { data, uti, orientation, info ->
//
//    Logger.d("third", "data: $data")
//    data?.let {
//      NSURL.absoluteURLWithDataRepresentation(it, null).let { nsurl ->
//        Logger.d("third", "url: $nsurl")
//        callback(nsurl)
//      }
//    }
//
////    callback(fileURL?.absoluteString)
//  }
}