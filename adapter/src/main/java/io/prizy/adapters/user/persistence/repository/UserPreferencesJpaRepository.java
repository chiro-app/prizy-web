package io.prizy.adapters.user.persistence.repository;

import java.util.UUID;

import io.prizy.adapters.user.persistence.entity.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:26 PM
 */


public interface UserPreferencesJpaRepository extends JpaRepository<UserPreferencesEntity, UUID> {

}
