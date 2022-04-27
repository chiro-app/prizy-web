package io.prizy.domain.contest.mapper;


import io.prizy.domain.contest.command.CreateContest;
import io.prizy.domain.contest.model.Contest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreateContestMapper {

  public Contest map(CreateContest command) {
    return new Contest(
      null,
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
