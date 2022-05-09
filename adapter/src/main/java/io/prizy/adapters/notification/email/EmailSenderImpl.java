package io.prizy.adapters.notification.email;

import javax.mail.internet.MimeMessage;

import io.prizy.domain.notification.port.EmailSender;
import io.prizy.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:09 PM
 */


@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

  private final JavaMailSender mailSender;
  private final MailProperties emailProperties;

  @Override
  public void send(User user, String subject, String content) {
    var messageCreator = (MimeMessagePreparator) (MimeMessage mimeMessage) -> {
      var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
      helper.setFrom(emailProperties.getProperties().get("sender"));
      helper.setTo(user.email());
      helper.setSubject(subject);
      helper.setText(content, true);
    };
    mailSender.send(messageCreator);
  }
}
