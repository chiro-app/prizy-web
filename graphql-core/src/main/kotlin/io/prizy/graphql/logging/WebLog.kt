package io.prizy.graphql.logging

import java.time.Instant

/**
 * @author Nidhal Dogga
 * @created 01/06/2022 18:03
 */

data class WebLog(
  val type: QueryType,
  val timestamp: Instant,
  val elapsed: Long,
  val headers: Map<String, String>,
  val ip: String?,
  val query: String,
  val response: Any?,
  val variables: Map<String, Any?>? = null
)

enum class QueryType {
  QUERY,
  MUTATION
}
