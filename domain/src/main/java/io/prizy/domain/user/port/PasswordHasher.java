package io.prizy.domain.user.port;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:14 PM
 */


public interface PasswordHasher {
  String hash(String password);
}
