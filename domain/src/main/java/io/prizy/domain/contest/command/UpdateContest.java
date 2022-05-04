package io.prizy.domain.contest.command;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.model.ContestCategory;
import io.prizy.domain.contest.model.Merchant;
import lombok.Builder;


@Builder
public record UpdateContest(
  UUID id,
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
