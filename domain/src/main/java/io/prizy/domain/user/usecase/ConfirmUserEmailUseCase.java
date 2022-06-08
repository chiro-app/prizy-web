package io.prizy.domain.user.usecase;

import io.prizy.domain.user.model.UserStatus;
import io.prizy.domain.user.port.ConfirmationCodeRepository;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:54
 */


@Component
@RequiredArgsConstructor
public class ConfirmUserEmailUseCase {


  private final UserRepository userRepository;
  private final ConfirmationCodeRepository confirmationCodeRepository;

  @Transactional
  public Boolean confirm(String code) {
    var maybeConfirmationCode = confirmationCodeRepository.byCode(code);
    if (maybeConfirmationCode.isEmpty()) {
      return false;
    }
    var confirmationCode = maybeConfirmationCode.get();
    var user = userRepository.byId(confirmationCode.userId()).get();
    userRepository.save(user.withStatus(UserStatus.CONFIRMED));
    confirmationCodeRepository.deleteByCode(code);
    return true;
  }

}
