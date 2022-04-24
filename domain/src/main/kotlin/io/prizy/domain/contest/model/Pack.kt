package io.prizy.domain.contest.model

import java.time.Instant
import java.util.UUID

sealed interface Pack {

  val id: UUID
  val name: String
  val lastWinnerPosition: Int
  val firstWinnerPosition: Int

  data class Product(
    override val id: UUID,
    override val name: String,
    override val lastWinnerPosition: Int,
    override val firstWinnerPosition: Int,
    val quantity: Int,
    val value: Float,
    val mediaId: String
  ) : Pack

  data class Coupon(
    override val id: UUID,
    override val name: String,
    override val lastWinnerPosition: Int,
    override val firstWinnerPosition: Int,
    val code: String,
    val expiration: Instant,
  ) : Pack
}



