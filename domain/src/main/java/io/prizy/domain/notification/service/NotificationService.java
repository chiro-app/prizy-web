package io.prizy.domain.notification.service;

import io.prizy.domain.notification.model.EmailNotification;
import io.prizy.domain.notification.model.PushNotification;
import io.prizy.domain.notification.port.EmailSender;
import io.prizy.domain.notification.port.PushNotificationSender;
import io.prizy.domain.notification.port.TemplateCompiler;
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

  private final TemplateCompiler templateCompiler;
  private final EmailSender emailSender;
  private final PushNotificationSender pushNotificationSender;
  private final UserRepository userRepository;

  public void send(EmailNotification email) {
    var user = userRepository.byId(email.userId()).get();
    var content = templateCompiler.compileTemplate(email.templateName(), email.data());
    emailSender.send(user, email.subject(), content);
  }

  public void send(PushNotification push) {
    var subject = templateCompiler.compileRaw(push.subject(), push.data());
    var content = templateCompiler.compileRaw(push.content(), push.data());
    if (push instanceof PushNotification.SingleUser singleUser) {
      var user = userRepository.byId(singleUser.userId()).get();
      pushNotificationSender.send(user, subject, content);
    } else if (push instanceof PushNotification.AllUsers) {
      pushNotificationSender.sendToAllUsers(subject, content);
    }
  }

}
