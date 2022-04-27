package io.prizy.domain.contest.mapper;


import io.prizy.domain.contest.command.UpdateContest;
import io.prizy.domain.contest.model.Contest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UpdateContestMapper {

  public Contest map(UpdateContest command) {
    return new Contest(
      command.id(),
      command.name(),
      command.description(),
      command.category(),
      command.fromDate(),
      command.toDate(),
      command.mediaIds(),
      command.coverMediaId(),
      command.packs().stream().map(CreatePackMapper::map).toList(),
      command.cost(),
      command.facebookPage(),
      command.instagramPage(),
      command.website(),
      command.newsletterSubscription(),
      command.adultOnly(),
      command.merchant(),
      command.boardId()
    );
  }

}
