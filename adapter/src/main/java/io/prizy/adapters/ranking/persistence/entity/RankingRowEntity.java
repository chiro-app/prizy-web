package io.prizy.adapters.ranking.persistence.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Nidhal Dogga
 * @created 5/2/2022 9:38 AM
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RankingRow")
@Table(name = "ranking_rows")
public class RankingRowEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private Long score;
  @Column(name = "contest_id")
  private UUID contestId;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "created")
  private Instant dateTime;

}
