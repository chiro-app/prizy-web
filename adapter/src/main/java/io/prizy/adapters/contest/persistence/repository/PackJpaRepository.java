package io.prizy.adapters.contest.persistence.repository;

import java.util.UUID;

import io.prizy.adapters.contest.persistence.entity.PackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 3:59 PM
 */

public interface PackJpaRepository extends JpaRepository<PackEntity, UUID> {

}
