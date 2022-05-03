package io.prizy.graphql.directives

import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import com.expediagroup.graphql.generator.extensions.deepName
import graphql.execution.DataFetcherResult
import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLInputObjectField
import graphql.schema.GraphQLNonNull
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.exception.AuthenticationRequiredException
import io.prizy.graphql.exception.GraphQLException
import io.prizy.graphql.exception.InsufficientCredentialsException
import io.prizy.toolbox.exception.ErrorCode
import io.prizy.toolbox.exception.InternalServerException
import org.slf4j.LoggerFactory
import kotlin.reflect.full.findAnnotation

/**
 *  @author Nidhal Dogga
 *  @created 11/27/2021 11:57 AM
 *  @credits prizy.io
 *  All rights reserved
 */

class AuthorizedSchemaDirectiveWiring : KotlinSchemaDirectiveWiring {

  companion object {
    private val log = LoggerFactory.getLogger(AuthorizedSchemaDirectiveWiring::class.java)
  }

  @Suppress("UNCHECKED_CAST")
  override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
    val field = environment.element
    val authorizedDirective = environment.directive
    val originalDataFetcher = environment.getDataFetcher()

    environment.setDataFetcher { dfe ->
      try {
        val permissions = dfe.getContext<GraphQLContext>().let { context ->
          when (context) {
            is GraphQLContext.Authenticated -> context.authorizations
            else -> throw AuthenticationRequiredException()
          }
        }

        val requiredRoles = authorizedDirective.getArgument("roles").argumentValue.value as Array<String>
        val targetResource = authorizedDirective.getArgument("resource").argumentValue.value as String
        val requiredScope = authorizedDirective.getArgument("scope").argumentValue.value as String

        if (!permissions.hasRole(requiredRoles.toList())) {
          throw InsufficientCredentialsException()
        }

        if ((targetResource != "" && requiredScope == "") || (targetResource == "" && requiredScope != "")) {
          log.error("Missing scope for resource $targetResource")
          throw InternalServerException()
        }

        if (targetResource != "") {
          val resourceId = getResourceId(dfe)
          if (resourceId == null) {
            log.error("Missing resource id field for resource $targetResource")
            throw InternalServerException()
          }
          if (!permissions.hasPermission(targetResource, resourceId, requiredScope)) {
            log.error("Insufficient permissions for resource $targetResource")
            throw InsufficientCredentialsException()
          }
        }

        originalDataFetcher.get(dfe)
      } catch (exception: Throwable) {
        DataFetcherResult
          .newResult<Any?>()
          .error(
            GraphQLException(
              locations = listOf(dfe.operationDefinition.sourceLocation),
              errorCode = exception::class.findAnnotation<ErrorCode>()?.value ?: "INTERNAL_SERVER",
            )
          )
          .build()
      }
    }

    return field
  }

  private fun getResourceId(dfe: DataFetchingEnvironment): String? {
    return getResourceIdFromInlineArgs(dfe) ?: getResourceIdFromInputFields(dfe)
  }

  private fun getResourceIdFromInlineArgs(dfe: DataFetchingEnvironment): String? {
    return dfe.fieldDefinition.arguments
      .firstOrNull { a -> a.directives.any { d -> d.name == ResourceIdentifierDirective.NAME } }
      ?.name
      ?.let { argName -> dfe.arguments[argName] as? String }
  }

  private fun getResourceIdFromInputFields(dfe: DataFetchingEnvironment): String? {
    return dfe.fieldDefinition.arguments
      .mapNotNull { arg -> arg.type as? GraphQLNonNull }
      .map { arg ->
        arg.deepName to arg.originalWrappedType.children
          .mapNotNull { field -> field as? GraphQLInputObjectField }
          .firstOrNull { field -> field.getDirective(ResourceIdentifierDirective.NAME) != null }
      }
      .firstOrNull { (_, field) -> field != null }
      ?.let { (argName, field) -> dfe.fieldDefinition.arguments.first { a -> a.type.deepName == argName }.name to field?.name }
      ?.let { (argName, fieldName) -> (dfe.arguments[argName] as Map<*, *>)[fieldName] } as? String
  }
}