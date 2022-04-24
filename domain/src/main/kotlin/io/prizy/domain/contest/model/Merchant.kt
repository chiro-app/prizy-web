package io.prizy.domain.contest.model

/**
 *  @author Nidhal Dogga
 *  @created 01/03/2021 21:18
 *  @credits prizy.io
 *  All rights reserved
 */

data class Merchant(
  val name: String,
  val siren: String,
  val capital: Float,
  val address: String,
  val logoMediaId: String
)
