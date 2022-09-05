package io.prizy.adapters.ranking.persistence;

import java.util.List;
import java.util.UUID;

import io.prizy.adapters.ranking.persistence.entity.RankingRowEntity;
import io.prizy.adapters.ranking.persistence.repository.RankingRowJpaRepository;
import io.prizy.domain.ranking.model.RankingRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Nidhal Dogga
 * @created 15/07/2022 12:21
 */


@ExtendWith(MockitoExtension.class)
class RankingRepositoryImplTest {

  private static final UUID CONTEST_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

  private static final UUID USER_ID_00 = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private static final UUID USER_ID_01 = UUID.fromString("00000000-0000-0000-0000-000000000001");
  private static final UUID USER_ID_02 = UUID.fromString("00000000-0000-0000-0000-000000000002");
  private static final UUID USER_ID_03 = UUID.fromString("00000000-0000-0000-0000-000000000003");
  private static final UUID USER_ID_04 = UUID.fromString("00000000-0000-0000-0000-000000000004");

  @Mock
  private RankingRowJpaRepository jpaRepository;

  @InjectMocks
  private RankingRepositoryImpl repository;

  @BeforeEach
  void setup() {
    when(jpaRepository.findAllByContestId(CONTEST_ID))
      .thenReturn(List.of(
        RankingRowEntity.builder()
          .score(123L)
          .contestId(CONTEST_ID)
          .userId(USER_ID_00)
          .build(),
        RankingRowEntity.builder()
          .score(0L)
          .contestId(CONTEST_ID)
          .userId(USER_ID_01)
          .build(),
        RankingRowEntity.builder()
          .score(10_000_000_000L)
          .contestId(CONTEST_ID)
          .userId(USER_ID_02)
          .build(),
        RankingRowEntity.builder()
          .score(1L)
          .contestId(CONTEST_ID)
          .userId(USER_ID_03)
          .build()
      ));
  }

  @Test
  @DisplayName("Should sort rows correctly")
  void shouldSortRows() {
    var rows = (List<RankingRow>) repository.byContestId(CONTEST_ID);
    var firstRow = rows.get(0);
    var secondRow = rows.get(1);
    var thirdRow = rows.get(2);
    var fourthRow = rows.get(3);
    assertThat(firstRow.score()).isGreaterThanOrEqualTo(secondRow.score());
    assertThat(secondRow.score()).isGreaterThanOrEqualTo(thirdRow.score());
    assertThat(thirdRow.score()).isGreaterThanOrEqualTo(fourthRow.score());
  }

  @Test
  @DisplayName("Should return correct user ranking")
  void shouldReturnCorrectUserRanking() {
    var userRanking = repository.rankingOfUserInContest(USER_ID_00, CONTEST_ID);
    assertThat(userRanking)
      .isNotEmpty()
      .hasValue(1);
    userRanking = repository.rankingOfUserInContest(USER_ID_04, UUID.randomUUID());
    assertThat(userRanking).isEmpty();
  }
}