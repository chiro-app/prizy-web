package io.prizy.adapters.user.persistence.entity;


import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import io.prizy.domain.auth.model.Roles;
import io.prizy.domain.user.model.Gender;
import io.prizy.domain.user.model.UserCreationOrigin;
import io.prizy.domain.user.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users")
@TypeDef(
  name = "list-array",
  typeClass = ListArrayType.class
)
public class UserEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column(unique = true)
  private String email;
  @Column
  private String passwordHash;
  @Column
  private String username;
  @Column(name = "birth_date")
  private LocalDate birthDate;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "avatar_media_id")
  private String avatarMediaId;
  @Column(name = "mobile_phone")
  private String mobilePhone;
  @Builder.Default
  @Type(type = "list-array")
  @Column(columnDefinition = "text[]")
  private Collection<String> roles = List.of(Roles.USER);
  @Column
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private UserCreationOrigin origin = UserCreationOrigin.EMAIL;
  @Column
  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Column
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private UserStatus status = UserStatus.PENDING;
  @Column
  @CreatedDate
  @Builder.Default
  private Instant created = Instant.now();
  @Column(name = "address_id", insertable = false, updatable = false)
  private UUID addressId;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private AddressEntity address;

}
