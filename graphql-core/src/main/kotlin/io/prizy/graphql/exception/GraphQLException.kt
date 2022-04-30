package io.prizy.graphql.exception

import graphql.ErrorClassification
import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 11:49 AM
 */


class GraphQLException(
  private val errorCode: String,
  private val extras: Map<String, Any> = emptyMap(),
  private val locations: List<SourceLocation> = emptyList(),
  private val path: List<Any>? = null
) : GraphQLError {

  override fun getMessage(): String? = null

  override fun getPath(): List<Any>? = path

  override fun getLocations(): List<SourceLocation> = locations

  override fun getErrorType(): ErrorClassification = ErrorType.DataFetchingException

  override fun getExtensions(): Map<String, Any> = buildMap {
    set("errorCode", errorCode)
    putAll(extras)
  }

}