package io.prizy.adapters.user.persistence;

import java.util.Optional;

import io.prizy.adapters.user.mapper.ConfirmationCodeMapper;
import io.prizy.adapters.user.persistence.repository.ConfirmationCodeJpaRepository;
import io.prizy.domain.user.model.ConfirmationCode;
import io.prizy.domain.user.port.ConfirmationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:50 PM
 */


@Component
@RequiredArgsConstructor
public class ConfirmationCodeRepositoryImpl implements ConfirmationCodeRepository {

  private final ConfirmationCodeJpaRepository jpaRepository;

  @Override
  public Optional<ConfirmationCode> byCode(String code) {
    return jpaRepository.findByCode(code).map(ConfirmationCodeMapper::map);
  }

  @Override
  public ConfirmationCode save(ConfirmationCode confirmationCode) {
    return ConfirmationCodeMapper.map(jpaRepository.save(ConfirmationCodeMapper.map(confirmationCode)));
  }

  @Override
  public void deleteByCode(String code) {
    jpaRepository.deleteByCode(code);
  }

}
