package io.prizy.adapters.user.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:47 PM
 */


@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Address")
@Table(name = "addresses")
public class AddressEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column(name = "user_id", updatable = false, insertable = false)
  private UUID userId;
  @Column
  private String street;
  @Column
  private String city;
  @Column
  private String country;
  @Column(name = "zipcode")
  private String zipCode;
  @Column(name = "extra_line_1")
  private String extraLine1;
  @Column(name = "extra_line_2")
  private String extraLine2;
  @OneToOne
  private UserEntity user;

}
