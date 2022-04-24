package io.prizy.adapters.contest.persistence.entity

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

/**
 *  @author Nidhal Dogga
 *  @created 01/03/2021 21:21
 *  @credits prizy.io
 *  All rights reserved
 */

@Entity(name = "Merchant")
@Table(name = "merchants")
class MerchantEntity(
  @Id
  @GeneratedValue
  val id: UUID? = null,
  @Column
  val name: String,
  @Column
  val siren: String,
  @Column
  val capital: Float,
  @Column
  val address: String,
  @Column(name = "logo_media_id")
  val logoMediaId: String
)
