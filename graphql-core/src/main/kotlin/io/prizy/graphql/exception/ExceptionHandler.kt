package io.prizy.graphql.exception

import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import io.prizy.toolbox.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

/**
 * @author Nidhal Dogga
 * @created 11/05/2022 10:30
 */

@Component
class ExceptionHandler : DataFetcherExceptionHandler {

  companion object {
    private val log = LoggerFactory.getLogger(ExceptionHandler::class.java)
  }

  override fun onException(parameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
    val exceptionClass = parameters.exception::class
    val errorCode = when {
      exceptionClass.hasAnnotation<ErrorCode>() -> exceptionClass.findAnnotation<ErrorCode>()!!.value
      else -> {
        log.error("Unexpected exception {}", parameters.exception)
        "INTERNAL_SERVER_ERROR"
      }
    }

    val sourceLocation = parameters.sourceLocation
    val path = parameters.path

    val error = GraphQLException(
      errorCode = errorCode,
      locations = listOf(sourceLocation),
      path = path.toList()
    )

    return DataFetcherExceptionHandlerResult.newResult(error).build()
  }
}