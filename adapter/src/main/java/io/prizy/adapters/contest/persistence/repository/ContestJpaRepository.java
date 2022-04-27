package io.prizy.adapters.contest.persistence.repository;

import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:13 PM
 */

public interface ContestJpaRepository extends JpaRepository<ContestEntity, UUID> {

}
