package io.prizy.domain.contest.mapper;


import io.prizy.domain.contest.command.UpdateContest;
import io.prizy.domain.contest.model.Contest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UpdateContestMapper {

  public Contest map(UpdateContest create) {
    return Contest.builder()
      .id(create.id())
      .name(create.name())
      .description(create.description())
      .category(create.category())
      .fromDate(create.fromDate())
      .toDate(create.toDate())
      .assetIds(create.assetIds())
      .coverAssetId(create.coverAssetId())
      .packs(create.packs().stream().map(CreatePackMapper::map).toList())
      .cost(create.cost())
      .facebookPage(create.facebookPage())
      .instagramPage(create.instagramPage())
      .website(create.website())
      .newsletterSubscription(create.newsletterSubscription())
      .adultOnly(create.adultOnly())
      .merchant(create.merchant())
      .boardId(create.boardId())
      .build();
  }

}
