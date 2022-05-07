package io.prizy.domain.user.service;

import java.util.Map;
import java.util.UUID;

import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.user.model.ConfirmationCode;
import io.prizy.domain.user.model.UserStatus;
import io.prizy.domain.user.port.ConfirmationCodeRepository;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:38 PM
 */


@Service
@Transactional
@RequiredArgsConstructor
public class EmailConfirmationService {

  private static final Integer CONFIRMATION_CODE_LENGTH = 6;
  private static final String CONFIRMATION_CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String CONFIRMATION_EMAIL_SUBJECT = "%s - Bienvenue sur Chiro";
  private static final String CONFIRMATION_EMAIL_TEMPLATE_NAME = "email-confirmation";

  private final ConfirmationCodeRepository repository;
  private final UserRepository userRepository;

  private final NotificationPublisher notificationPublisher;

  public void sendConfirmationEmail(UUID userId) {
    var code = RandomStringUtils.random(CONFIRMATION_CODE_LENGTH, CONFIRMATION_CODE_ALPHABET);
    repository.save(new ConfirmationCode(code, userId));
    var user = userRepository.byId(userId).get();
    notificationPublisher.publish(new SendEmail(
      new EmailNotification(
        userId,
        String.format(CONFIRMATION_EMAIL_SUBJECT, code),
        CONFIRMATION_EMAIL_TEMPLATE_NAME,
        Map.of(
          "confirmationCode", code,
          "firstName", user.firstName()
        )
      )
    ));
  }

  public Boolean confirmEmail(String code) {
    var maybeConfirmationCode = repository.byCode(code);
    if (maybeConfirmationCode.isEmpty()) {
      return false;
    }
    var confirmationCode = maybeConfirmationCode.get();
    var user = userRepository.byId(confirmationCode.userId()).get();
    userRepository.save(user.withStatus(UserStatus.CONFIRMED));
    repository.deleteByCode(code);
    return true;
  }

}
