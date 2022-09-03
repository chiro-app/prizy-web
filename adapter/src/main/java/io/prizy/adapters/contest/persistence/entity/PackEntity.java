package io.prizy.adapters.contest.persistence.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Pack")
@Table(name = "packs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PackEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private String name;
  @Column(name = "first_winner_position")
  private Integer firstWinnerPosition;
  @Column(name = "last_winner_position")
  private Integer lastWinnerPosition;
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "contest_id")
  private ContestEntity contest;

  @Getter
  @SuperBuilder
  @NoArgsConstructor
  @Entity(name = "Product")
  public static class Product extends PackEntity {

    @Column
    private Double value;
    @Column(name = "asset_id")
    private String assetId;
    @Column
    private Integer quantity;

  }

  @Getter
  @SuperBuilder
  @NoArgsConstructor
  @Entity(name = "Coupon")
  public static class Coupon extends PackEntity {

    @Column
    private String code;
    @Column(name = "expiration_date")
    private Instant expiration;

  }
}
