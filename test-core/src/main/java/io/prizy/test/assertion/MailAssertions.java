package io.prizy.test.assertion;

import java.util.Arrays;
import javax.mail.MessagingException;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.icegreen.greenmail.util.ServerSetupTest.SMTP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author Nidhal Dogga
 * @created 8/23/2022 10:04 PM
 */

public interface MailAssertions {

  @RegisterExtension
  GreenMailExtension GREEN_MAIL_EXTENSION = new GreenMailExtension(SMTP);

  default void assertThatEmailSent(String recipient, String subject) {
    assertThat(GREEN_MAIL_EXTENSION.getReceivedMessages())
      .anyMatch(mimeMessage -> {
          try {
            return mimeMessage.getSubject().equals(subject) &&
              Arrays
                .stream(mimeMessage.getAllRecipients())
                .anyMatch(address -> address.toString().equals(recipient));
          } catch (MessagingException e) {
            fail(e.getMessage());
          }
          return false;
        }
      );
  }

}
