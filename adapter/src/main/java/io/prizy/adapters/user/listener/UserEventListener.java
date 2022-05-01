package io.prizy.adapters.user.listener;

import io.prizy.domain.user.event.UserCreated;
import io.prizy.domain.user.event.UserUpdated;
import io.prizy.domain.user.service.EmailConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:37 PM
 */


@Component
@RequiredArgsConstructor
public class UserEventListener {

  private final EmailConfirmationService emailConfirmationService;

  @EventListener
  public void onUserCreated(UserCreated event) {
    emailConfirmationService.sendConfirmationEmail(event.user().id());
  }

  @EventListener
  public void onUserUpdated(UserUpdated event) {

  }

}
