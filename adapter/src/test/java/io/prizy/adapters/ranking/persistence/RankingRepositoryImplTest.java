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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Nidhal Dogga
 * @created 15/07/2022 12:21
 */


@ExtendWith(MockitoExtension.class)
class RankingRepositoryImplTest {

  @Mock
  private RankingRowJpaRepository jpaRepository;

  @InjectMocks
  private RankingRepositoryImpl repository;

  @BeforeEach
  void setup() {
    when(jpaRepository.findAllByContestId(any()))
      .thenReturn(List.of(
        RankingRowEntity.builder()
          .score(123L)
          .userId(UUID.randomUUID())
          .build(),
        RankingRowEntity.builder()
          .score(0L)
          .userId(UUID.randomUUID())
          .build(),
        RankingRowEntity.builder()
          .score(10_000_000_000L)
          .userId(UUID.randomUUID())
          .build(),
        RankingRowEntity.builder()
          .score(1L)
          .userId(UUID.randomUUID())
          .build()
      ));
  }

  @Test
  @DisplayName("Should sort rows correctly")
  void shouldSortRows() {
    var rows = (List<RankingRow>) repository.byContestId(UUID.randomUUID());
    var firstRow = rows.get(0);
    var secondRow = rows.get(1);
    var thirdRow = rows.get(2);
    var fourthRow = rows.get(3);
    assertThat(firstRow.score()).isGreaterThanOrEqualTo(secondRow.score());
    assertThat(secondRow.score()).isGreaterThanOrEqualTo(thirdRow.score());
    assertThat(thirdRow.score()).isGreaterThanOrEqualTo(fourthRow.score());
  }

}