package io.prizy.publicapi.auth.exception

import io.prizy.toolbox.exception.ErrorCode

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 8:46 PM
 */

@ErrorCode("INVALID_CREDENTIALS")
class InvalidCredentialsException : RuntimeException()