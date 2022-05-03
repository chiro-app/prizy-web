package io.prizy.adapters.asset.persistence.repository;

import io.prizy.adapters.asset.persistence.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 10:24
 */

public interface AssetJpaRepository extends JpaRepository<AssetEntity, String> {
}
