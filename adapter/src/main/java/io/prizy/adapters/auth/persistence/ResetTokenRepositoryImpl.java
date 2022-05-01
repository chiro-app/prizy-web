package io.prizy.adapters.auth.persistence;

import java.util.Optional;

import io.prizy.adapters.auth.mapper.ResetTokenMapper;
import io.prizy.adapters.auth.persistence.repository.ResetTokenJpaRepository;
import io.prizy.domain.auth.model.ResetToken;
import io.prizy.domain.auth.port.ResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:43 AM
 */


@Component
@RequiredArgsConstructor
public class ResetTokenRepositoryImpl implements ResetTokenRepository {

  private final ResetTokenJpaRepository jpaRepository;

  @Override
  public ResetToken save(ResetToken resetToken) {
    return ResetTokenMapper.map(jpaRepository.save(ResetTokenMapper.map(resetToken)));
  }

  @Override
  public Optional<ResetToken> byToken(String token) {
    return jpaRepository.findByToken(token).map(ResetTokenMapper::map);
  }

  @Override
  public void delete(ResetToken resetToken) {
    jpaRepository.deleteByToken(resetToken.token());
  }

}
