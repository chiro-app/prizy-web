package io.prizy.adapters.user;

import io.prizy.domain.user.port.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 6:11 PM
 */


@Component
@RequiredArgsConstructor
public class BCryptPasswordHasher implements PasswordHasher {

  private final PasswordEncoder passwordEncoder;

  @Override
  public String hash(String password) {
    return passwordEncoder.encode(password);
  }

}
