package io.prizy.graphql.instrumentation

import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import io.prizy.graphql.logging.GraphQLWebLogger
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.time.Instant

/**
 * @author Nidhal Dogga
 * @created 08/09/2022 10:24
 */

@Component
@ConditionalOnProperty("prizy.graphql.logging.enabled", havingValue = "true")
class WebLoggingInstrumentation(private val webLogger: GraphQLWebLogger) : SimpleInstrumentation() {

  override fun beginExecution(parameters: InstrumentationExecutionParameters): InstrumentationContext<ExecutionResult> {
      val from = Instant.now()
      return object : SimpleInstrumentationContext<ExecutionResult>() {
        override fun onCompleted(result: ExecutionResult?, t: Throwable?) {
          if (parameters.operation == null || !parameters.operation.equals("IntrospectionQuery")) {
            val to = Instant.now()
            webLogger.logRequest(parameters, from, to, result, t)
          }
        }
      }
  }
}