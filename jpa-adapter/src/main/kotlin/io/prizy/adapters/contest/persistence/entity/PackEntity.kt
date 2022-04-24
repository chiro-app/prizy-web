package io.prizy.adapters.contest.persistence.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity(name = "Pack")
@Table(name = "packs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
sealed class PackEntity(
  @Id
  @GeneratedValue
  open var id: UUID? = null,
  @Column
  open var name: String,
  @Column(name = "first_winner_position")
  open var firstWinnerPosition: Int,
  @Column(name = "last_winner_position")
  open var lastWinnerPosition: Int,
  @ManyToOne
  @JoinColumn(name = "contest_id")
  open var contest: ContestEntity? = null
) {

  @Entity(name = "Product")
  class Product(
    override var id: UUID? = null,
    override var name: String,
    override var firstWinnerPosition: Int,
    override var lastWinnerPosition: Int,
    @Column
    var value: Float,
    @Column(name = "media_id")
    var mediaId: String,
    @Column
    var quantity: Int,
  ) : PackEntity(id, name, firstWinnerPosition, lastWinnerPosition)

  @Entity(name = "Coupon")
  class Coupon(
    override var id: UUID? = null,
    override var name: String,
    override var firstWinnerPosition: Int,
    override var lastWinnerPosition: Int,
    @Column
    var code: String,
    @Column(name = "expiration_date")
    var expiration: Instant,
  ) : PackEntity(id, name, firstWinnerPosition, lastWinnerPosition)
}
