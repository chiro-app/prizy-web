package io.prizy.domain.auth.service;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.auth.port.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 11:06 PM
 */


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private static final Integer REFRESH_TOKEN_LENGTH = 64;

  private final RefreshTokenRepository repository;

  public String getOrCreate(UUID userId) {
    return repository
      .getForUser(userId)
      .orElseGet(() -> repository.save(userId, RandomStringUtils.randomAlphabetic(REFRESH_TOKEN_LENGTH)));
  }

  public Optional<UUID> findUserId(String token) {
    return repository.findUserId(token);
  }

}
