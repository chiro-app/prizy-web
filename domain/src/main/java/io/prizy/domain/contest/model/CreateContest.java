package io.prizy.domain.contest.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateContest(
  String name,
  String description,
  ContestCategory category,
  Instant fromDate,
  Instant toDate,
  Collection<String> assetIds,
  String coverAssetId,
  Collection<CreatePack> packs,
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
