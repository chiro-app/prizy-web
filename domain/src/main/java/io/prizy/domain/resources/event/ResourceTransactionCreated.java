package io.prizy.domain.resources.event;

import io.prizy.domain.resources.model.ResourceTransaction;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:02
 */

public record ResourceTransactionCreated(
  ResourceTransaction transaction
) {
}
