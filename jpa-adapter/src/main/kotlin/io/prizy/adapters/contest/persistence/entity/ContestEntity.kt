package io.prizy.adapters.contest.persistence.entity

import io.prizy.domain.contest.model.ContestCategory
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import java.time.Instant
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:14 PM
 */

@Entity(name = "Contest")
@Table(name = "contests")
class ContestEntity(
  @Id
  var id: UUID,
  @Column
  var name: String,
  @Column(name = "from_date")
  var fromDate: Instant,
  @Column(name = "to_date")
  var toDate: Instant,
  @Column
  var description: String,
  @Column
  @Enumerated(EnumType.STRING)
  var category: ContestCategory,
  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "media_ids")
  var mediaIds: Set<String> = emptySet(),
  @Column(name = "cover_media_id")
  var coverMediaId: String,
  @Column
  var cost: Int,
  @Column(name = "facebook_page")
  var facebookPage: String?,
  @Column(name = "instagram_page")
  var instagramPage: String?,
  @Column
  var website: String?,
  @Column(name = "newsletter_subscription")
  var newsletterSubscription: Boolean,
  @Column(name = "adult_only")
  var adultOnly: Boolean,
  @Fetch(value = FetchMode.SELECT)
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "contest", targetEntity = PackEntity::class, fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
  var packs: Set<PackEntity> = emptySet(),
  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  var merchant: MerchantEntity,
  @Column(name = "board_id")
  var boardId: UUID
)
