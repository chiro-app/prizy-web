package io.prizy.adapters.contest.mapper;

import java.util.Optional;
import java.util.stream.Collectors;

import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import io.prizy.domain.contest.model.Contest;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:16 PM
 */

@UtilityClass
public class ContestMapper {

  public Contest map(ContestEntity entity) {
    return new Contest(
      entity.getId(),
      entity.getName(),
      entity.getDescription(),
      entity.getCategory(),
      entity.getFromDate(),
      entity.getToDate(),
      entity.getAssetIds(),
      entity.getCoverAssetId(),
      entity.getPacks().stream().map(PackMapper::map).toList(),
      entity.getCost(),
      Optional.ofNullable(entity.getFacebookPage()),
      Optional.ofNullable(entity.getInstagramPage()),
      Optional.ofNullable(entity.getWebsite()),
      entity.getNewsletterSubscription(),
      entity.getAdultOnly(),
      MerchantMapper.map(entity.getMerchant()),
      entity.getAccessCode(),
      entity.getBoardId()
    );
  }

  public ContestEntity map(Contest contest) {
    return ContestEntity.builder()
      .id(contest.id())
      .name(contest.name())
      .description(contest.description())
      .fromDate(contest.fromDate())
      .toDate(contest.toDate())
      .adultOnly(contest.adultOnly())
      .category(contest.category())
      .coverAssetId(contest.coverAssetId())
      .assetIds(contest.assetIds())
      .cost(contest.cost())
      .facebookPage(contest.facebookPage().orElse(null))
      .instagramPage(contest.instagramPage().orElse(null))
      .website(contest.website().orElse(null))
      .newsletterSubscription(contest.newsletterSubscription())
      .merchant(MerchantMapper.map(contest.merchant()))
      .boardId(contest.boardId())
      .packs(contest.packs().stream().map(PackMapper::map).collect(Collectors.toSet()))
      .accessCode(contest.accessCode())
      .build();
  }
}
