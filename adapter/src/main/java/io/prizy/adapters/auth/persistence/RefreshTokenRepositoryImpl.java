package io.prizy.adapters.auth.persistence;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.auth.persistence.entity.RefreshTokenEntity;
import io.prizy.adapters.auth.persistence.repository.RefreshTokenJpaRepository;
import io.prizy.domain.auth.port.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 11:51 PM
 */


@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final RefreshTokenJpaRepository jpaRepository;

  @Override
  public Optional<String> getForUser(UUID userId) {
    return jpaRepository.findByUserId(userId).map(RefreshTokenEntity::getToken);
  }

  @Override
  public String save(UUID userId, String token) {
    return jpaRepository.save(RefreshTokenEntity.builder()
      .token(token)
      .userId(userId)
      .build()
    ).getToken();
  }

  @Override
  public Optional<UUID> findUserId(String token) {
    return jpaRepository.findByToken(token).map(RefreshTokenEntity::getUserId);
  }

}
