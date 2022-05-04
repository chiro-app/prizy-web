package io.prizy.adapters.contest.persistence.entity;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.prizy.domain.contest.model.ContestCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:14 PM
 */

@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Contest")
@Table(name = "contests")
public class ContestEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private String name;
  @Column(name = "from_date")
  private Instant fromDate;
  @Column(name = "to_date")
  private Instant toDate;
  @Column
  private String description;
  @Column
  @Enumerated(EnumType.STRING)
  private ContestCategory category;
  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "asset_ids")
  private Collection<String> assetIds;
  @Column(name = "cover_asset_id")
  private String coverAssetId;
  @Column
  private Integer cost;
  @Column(name = "facebook_page")
  private String facebookPage;
  @Column(name = "instagram_page")
  private String instagramPage;
  @Column
  private String website;
  @Column(name = "newsletter_subscription")
  private Boolean newsletterSubscription;
  @Column(name = "adult_only")
  private Boolean adultOnly;
  @OneToMany(
    mappedBy = "contest", fetch = FetchType.LAZY, orphanRemoval = true,
    targetEntity = PackEntity.class, cascade = CascadeType.ALL
  )
  private Set<PackEntity> packs;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private MerchantEntity merchant;
  @Column(name = "board_id")
  private UUID boardId;

}
