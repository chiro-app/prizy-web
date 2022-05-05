package io.prizy.adapters.game.persistence.repository;

import java.util.UUID;

import io.prizy.adapters.game.persistence.entity.GameBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:07
 */

public interface GameBoardJpaRepository extends JpaRepository<GameBoardEntity, UUID> {
}
