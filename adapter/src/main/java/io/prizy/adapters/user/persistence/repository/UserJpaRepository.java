package io.prizy.adapters.user.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.user.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:23 PM
 */


public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
  Boolean existsByEmailOrUsernameOrMobilePhone(String email, String username, String mobilePhone);

  Optional<UserEntity> findByEmailOrUsername(String email, String username);

  default Optional<UserEntity> findByEmailOrUsername(String email) {
    return findByEmailOrUsername(email, email);
  }
}
