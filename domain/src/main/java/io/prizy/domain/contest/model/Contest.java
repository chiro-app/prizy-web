package io.prizy.domain.contest.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:14 AM
 */


public record Contest(
  UUID id,
  String name,
  String description,
  ContestCategory category,
  Instant fromDate,
  Instant toDate,
  Collection<String> mediaIds,
  String coverMediaId,
  Collection<Pack> packs,
  Integer cost,
  Optional<String> facebookPage,
  Optional<String> instagramPage,
  Optional<String> website,
  Boolean newsletterSubscription,
  Boolean adultOnly,
  Merchant merchant,
  UUID boardId
) {
}