package io.prizy.adapters.user.persistence.repository;

import java.util.UUID;

import io.prizy.adapters.user.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:23 PM
 */


public interface AddressJpaRepository extends JpaRepository<AddressEntity, UUID> {
}
