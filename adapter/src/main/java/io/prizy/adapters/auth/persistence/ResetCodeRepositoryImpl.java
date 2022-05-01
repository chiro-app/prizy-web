package io.prizy.adapters.auth.persistence;

import java.util.Optional;

import io.prizy.adapters.auth.mapper.ResetCodeMapper;
import io.prizy.adapters.auth.persistence.repository.ResetCodeJpaRepository;
import io.prizy.domain.auth.model.ResetCode;
import io.prizy.domain.auth.port.ResetCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:39 AM
 */


@Component
@RequiredArgsConstructor
public class ResetCodeRepositoryImpl implements ResetCodeRepository {

  private final ResetCodeJpaRepository jpaRepository;

  @Override
  public ResetCode save(ResetCode resetCode) {
    return ResetCodeMapper.map(jpaRepository.save(ResetCodeMapper.map(resetCode)));
  }

  @Override
  public Optional<ResetCode> byCode(String code) {
    return jpaRepository.findByCode(code).map(ResetCodeMapper::map);
  }

  @Override
  public void delete(ResetCode resetCode) {
    jpaRepository.deleteByCode(resetCode.code());
  }
}
