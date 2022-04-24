package io.prizy.toolbox.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.logging.Logger

/**
 *  @author Nidhal Dogga
 *  @created 4/25/2022 9:51 PM
 */

private val logger = Logger.getLogger("Coroutines")

suspend fun runDetached(block: suspend CoroutineScope.() -> Unit) = coroutineScope {
  launch {
    try {
      block()
    } catch (throwable: Throwable) {
      logger.severe(
        """
Exception in detached coroutine[${Thread.currentThread().name}]: ${throwable.message}
${throwable.stackTrace.joinToString("\n")}
      """.trimIndent()
      )
    }
  }
}