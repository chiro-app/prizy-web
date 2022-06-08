package io.prizy.domain.user.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.user.model.User;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:40 PM
 */


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public Optional<User> byId(UUID id) {
    return repository.byId(id);
  }

  public Optional<User> getUserByEmailOrUsername(String emailOrUsername) {
    return repository.byEmailOrUsername(emailOrUsername);
  }

  public Collection<User> listUsers() {
    return repository.list();
  }

}
