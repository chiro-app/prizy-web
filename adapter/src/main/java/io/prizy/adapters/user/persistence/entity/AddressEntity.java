package io.prizy.adapters.user.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:47 PM
 */


@Entity(name = "Address")
@Table(name = "addresses")
public class AddressEntity {

  @Id
  @GeneratedValue
  private UUID id;
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

}
