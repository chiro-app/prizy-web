package io.prizy.graphql.exception

import io.prizy.toolbox.exception.ErrorCode

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 11:46 AM
 */



@ErrorCode("INSUFFICIENT_CREDENTIALS")
class InsufficientCredentialsException : RuntimeException()
