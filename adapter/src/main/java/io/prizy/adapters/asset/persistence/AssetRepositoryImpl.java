package io.prizy.adapters.asset.persistence;

import java.util.Collection;
import java.util.Optional;

import io.prizy.adapters.asset.mapper.AssetMapper;
import io.prizy.adapters.asset.persistence.repository.AssetJpaRepository;
import io.prizy.domain.asset.model.Asset;
import io.prizy.domain.asset.port.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 7:45 AM
 */


@Component
@RequiredArgsConstructor
public class AssetRepositoryImpl implements AssetRepository {

  private final AssetJpaRepository jpaRepository;

  @Override
  public Asset save(Asset asset) {
    var entity = jpaRepository.save(AssetMapper.map(asset));
    return AssetMapper.map(entity);
  }

  @Override
  public Optional<Asset> byId(String id) {
    return jpaRepository.findById(id).map(AssetMapper::map);
  }

  @Override
  public Collection<Asset> byIds(Collection<String> ids) {
    return jpaRepository.findAllById(ids).stream().map(AssetMapper::map).toList();
  }

}
