package io.prizy.graphql.context

import graphql.schema.DataFetchingEnvironment
import io.prizy.graphql.auth.Authorizations
import io.prizy.graphql.auth.Principal
import io.prizy.toolbox.exception.AuthenticationRequiredException
import io.prizy.toolbox.exception.InternalServerException
import org.springframework.web.reactive.function.server.ServerRequest

const val REQUEST_CONTEST_PATH = "request"
const val PRINCIPAL_CONTEXT_PATH = "principal"
const val AUTHORIZATIONS_CONTEXT_PATH = "authorizations"

fun DataFetchingEnvironment.principalIfExists(): Principal? = graphQlContext[PRINCIPAL_CONTEXT_PATH]
fun DataFetchingEnvironment.authorizationsIfExists(): Authorizations? = graphQlContext[AUTHORIZATIONS_CONTEXT_PATH]

fun DataFetchingEnvironment.principal(): Principal =
  graphQlContext[PRINCIPAL_CONTEXT_PATH] ?: throw AuthenticationRequiredException()

fun DataFetchingEnvironment.authorizations(): Authorizations =
  graphQlContext[AUTHORIZATIONS_CONTEXT_PATH] ?: throw AuthenticationRequiredException()

fun DataFetchingEnvironment.request(): ServerRequest =
  graphQlContext[REQUEST_CONTEST_PATH] ?: throw InternalServerException()