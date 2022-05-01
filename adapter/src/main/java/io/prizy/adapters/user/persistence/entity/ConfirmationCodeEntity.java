package io.prizy.adapters.user.persistence.entity;

import java.time.Instant;
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
import org.springframework.data.annotation.CreatedDate;


/**
 * @author Nidhal Dogga
 * @created 1/1/2021 12:07
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ConfirmationCode")
@Table(name = "confirmation_codes")
public class ConfirmationCodeEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column
  private String code;
  @Column(name = "user_id")
  private UUID userId;
  @Column
  @CreatedDate
  private Instant created;

}
