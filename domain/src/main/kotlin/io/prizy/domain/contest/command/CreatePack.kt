package io.prizy.domain.contest.command

import java.time.Instant

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:58 AM
 */

sealed interface CreatePack {
  val name: String
  val firstWinnerPosition: Int
  val lastWinnerPosition: Int

  data class Product(
    override val name: String,
    override val firstWinnerPosition: Int,
    override val lastWinnerPosition: Int,
    val quantity: Int,
    val value: Float,
    val mediaId: String,
  ) : CreatePack

  data class Coupon(
    override val name: String,
    override val firstWinnerPosition: Int,
    override val lastWinnerPosition: Int,
    val code: String,
    val expiration: Instant,
  ) : CreatePack
}
