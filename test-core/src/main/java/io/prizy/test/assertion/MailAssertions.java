package io.prizy.test.assertion;

import java.util.Arrays;
import java.util.Collection;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  default GreenMailMultipleAssertion assertThatEmail() {
    return new GreenMailMultipleAssertion(GREEN_MAIL_EXTENSION.getReceivedMessages());
  }

  @RequiredArgsConstructor
  class GreenMailMultipleAssertion {

    private final MimeMessage[] messages;

    public GreenMailMultipleAssertion hasNumberOfEmails(int count) {
      assertThat(count).isEqualTo(messages.length);
      return this;
    }

    public GreenMailSingleAssertion atIndex(int index) {
      return new GreenMailSingleAssertion(messages[index]);
    }

  }

  @Slf4j
  @RequiredArgsConstructor
  class GreenMailSingleAssertion {

    private final MimeMessage message;

    public GreenMailSingleAssertion hasSubject(String subject) {
      try {
        assertThat(subject).isEqualTo(message.getSubject());
      } catch (MessagingException e) {
        fail(e.getMessage());
      }
      return this;
    }

    public GreenMailSingleAssertion hasRecipient(String recipient) {
      try {
        assertThat(message.getAllRecipients()).anyMatch(address -> recipient.equals(address.toString()));
      } catch (MessagingException e) {
        fail(e.getMessage());
      }
      return this;
    }

    public GreenMailSingleAssertion hasRecipients(Collection<String> recipients) {
      try {
        var allRecipients = message.getAllRecipients();
        assertThat(recipients).allMatch(recipient -> Arrays
          .stream(allRecipients)
          .anyMatch(address -> address.toString().equals(recipient)));
      } catch (MessagingException e) {
        fail(e.getMessage());
      }
      return this;
    }

  }

}
