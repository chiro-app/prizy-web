package io.prizy.domain.user.service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import io.prizy.domain.notification.event.SendEmail;
import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.publisher.NotificationPublisher;
import io.prizy.domain.user.model.ConfirmationCode;
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
@RequiredArgsConstructor
public class EmailConfirmationService {

  private static final Integer CONFIRMATION_CODE_LENGTH = 6;
  private static final String CONFIRMATION_CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String CONFIRMATION_EMAIL_SUBJECT = "%s - Bienvenue sur Chiro";
  private static final String CONFIRMATION_EMAIL_TEMPLATE_NAME = "email-confirmation.html";

  private final ConfirmationCodeRepository repository;
  private final UserRepository userRepository;

  private final NotificationPublisher notificationPublisher;

  @Transactional
  public void sendConfirmationEmail(UUID userId) {
    var code = RandomStringUtils.random(CONFIRMATION_CODE_LENGTH, CONFIRMATION_CODE_ALPHABET);
    repository.save(ConfirmationCode.builder()
      .code(code)
      .userId(userId)
      .created(Instant.now())
      .build()
    );
    var user = userRepository.byId(userId).get();
    notificationPublisher.publishEmail(new SendEmail(
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

}
