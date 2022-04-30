package io.prizy.adapters.user.persistence.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.UUID;


/**
 * @author Nidhal Dogga
 * @created 1/1/2021 12:07
 */


@Entity(name = "confirmation_codes")
public class ConfirmationCodeEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private String code;
  @Column(name = "user_id", insertable = false, updatable = false)
  private UUID userId;
  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;
  @Column
  @CreatedDate
  private Instant created;

}
