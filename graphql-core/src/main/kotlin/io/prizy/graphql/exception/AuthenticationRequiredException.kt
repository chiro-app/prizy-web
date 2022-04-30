package io.prizy.graphql.exception

import io.prizy.toolbox.exception.ErrorCode

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 11:47 AM
 */

@ErrorCode("AUTHENTICATION_REQUIRED")
class AuthenticationRequiredException : RuntimeException()