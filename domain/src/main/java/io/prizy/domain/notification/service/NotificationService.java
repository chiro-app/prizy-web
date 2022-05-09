package io.prizy.domain.notification.service;

import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.port.EmailSender;
import io.prizy.domain.notification.port.EmailTemplateCompiler;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:09 AM
 */


@Service
@RequiredArgsConstructor
public class NotificationService {

  private final EmailTemplateCompiler templateCompiler;
  private final EmailSender emailSender;
  private final UserRepository userRepository;

  public void send(EmailNotification email) {
    var user = userRepository.byId(email.userId()).get();
    var content = templateCompiler.compile(email.templateName(), email.data());
    emailSender.send(user, email.subject(), content);
  }

}
