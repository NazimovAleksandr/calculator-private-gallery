package com.next.level.solutions.calculator.fb.mp.utils

import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.darwin.DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL
import platform.darwin.dispatch_queue_create

actual class NetworkManager {
  actual fun runAfterNetworkConnection(block: () -> Unit) {
    // todo Working?
    val monitor = nw_path_monitor_create()
//    val queue = dispatch_get_main_queue()

    val queue = dispatch_queue_create(
      label = "dev.jordond.connectivity.monitor",
      attr = DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL,
    )

    nw_path_monitor_set_update_handler(monitor) { path ->
      Logger.d("NetworkManager", "path = $path")

      if (nw_path_get_status(path) == nw_path_status_satisfied) {
        Logger.d("NetworkManager", "Network is connected")
        nw_path_monitor_cancel(monitor)
        block()
      }
    }

    nw_path_monitor_set_queue(monitor, queue)
    nw_path_monitor_start(monitor)
  }
}