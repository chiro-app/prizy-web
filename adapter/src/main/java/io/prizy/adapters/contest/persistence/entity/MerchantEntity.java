package io.prizy.adapters.contest.persistence.entity;

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
 * @created 01/03/2021 21:21
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Merchant")
@Table(name = "merchants")
public class MerchantEntity {
  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private String name;
  @Column
  private String siren;
  @Column
  private Float capital;
  @Column
  private String address;
  @Column(name = "logo_asset_id")
  private String logoMediaId;
}
