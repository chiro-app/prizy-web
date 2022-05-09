package io.prizy.domain.auth.service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import io.prizy.domain.auth.exception.ResetCodeExpiredException;
import io.prizy.domain.auth.exception.ResetCodeNotFoundException;
import io.prizy.domain.auth.model.EditPassword;
import io.prizy.domain.auth.model.ResetCode;
import io.prizy.domain.auth.model.ResetToken;
import io.prizy.domain.auth.port.ResetCodeRepository;
import io.prizy.domain.auth.port.ResetTokenRepository;
import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.user.port.PasswordHasher;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:04 AM
 */


@Service
@Transactional
@RequiredArgsConstructor
public class PasswordResetService {

  private static final Integer RESET_CODE_LENGTH = 6;
  private static final Integer RESET_CODE_EXPIRE_SECONDS = 15 * 60;
  private static final String RESET_CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  private static final String RESET_PASSWORD_SUBJECT = "%s est votre code de verification sur Chiro";
  private static final String RESET_PASSWORD_TEMPLATE_NAME = "reset-password.html";
  private static final Integer RESET_TOKEN_LENGTH = 64;

  private final UserRepository userRepository;
  private final ResetCodeRepository resetCodeRepository;
  private final ResetTokenRepository resetTokenRepository;
  private final PasswordHasher passwordHasher;
  private final NotificationPublisher notificationPublisher;

  public Boolean sendResetCode(String loginId) {
    var maybeUser = userRepository.byEmailOrUsername(loginId);
    if (maybeUser.isEmpty()) {
      return false;
    }
    var user = maybeUser.get();
    var code = RandomStringUtils.random(RESET_CODE_LENGTH, RESET_CODE_ALPHABET);
    resetCodeRepository.save(new ResetCode(code, user.id(), Instant.now()));
    notificationPublisher.publish(new SendEmail(
      new EmailNotification(
        user.id(),
        String.format(RESET_PASSWORD_SUBJECT, code),
        RESET_PASSWORD_TEMPLATE_NAME,
        Map.of(
          "resetCode", code,
          "firstName", user.firstName()
        )
      )
    ));
    return true;
  }

  public String exchangeCodeForToken(String code) {
    var maybeResetCode = resetCodeRepository.byCode(code);
    if (maybeResetCode.isEmpty()) {
      throw new ResetCodeNotFoundException(code);
    }
    var resetCode = maybeResetCode.get();
    if (resetCode.created().isBefore(Instant.now().minusSeconds(RESET_CODE_EXPIRE_SECONDS))) {
      throw new ResetCodeExpiredException();
    }
    var resetToken = RandomStringUtils.randomAlphabetic(RESET_TOKEN_LENGTH);
    resetTokenRepository.save(new ResetToken(resetToken, resetCode.userId()));
    resetCodeRepository.delete(resetCode);
    return resetToken;
  }

  public Boolean resetPassword(String token, String password) {
    var maybeResetToken = resetTokenRepository.byToken(token);
    if (maybeResetToken.isEmpty()) {
      return false;
    }
    var resetToken = maybeResetToken.get();
    var user = userRepository
      .byId(resetToken.userId())
      .get()
      .withPasswordHash(passwordHasher.hash(password));
    userRepository.save(user);
    resetTokenRepository.delete(resetToken);
    return true;
  }

  public Boolean editPassword(UUID userId, EditPassword editPassword) {
    var user = userRepository.byId(userId);
    if (user.isEmpty() || !passwordHasher.matches(editPassword.oldPassword(), user.get().passwordHash())) {
      return false;
    }
    userRepository.update(user.get().withPasswordHash(passwordHasher.hash(editPassword.newPassword())));
    return true;
  }

}
