package io.prizy.domain.resources.exception;

import io.prizy.toolbox.exception.ErrorCode;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 14:56
 */


@ErrorCode("RESOURCE_BONUS_ALREADY_CLAIMED")
public class ResourceBonusAlreadyClaimedException extends RuntimeException {

}
