package io.prizy.adapters.user.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.user.mapper.UserMapper;
import io.prizy.adapters.user.persistence.repository.UserJpaRepository;
import io.prizy.domain.user.model.User;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:24 PM
 */


@Component
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository jpaRepository;

  @Override
  public User save(User user) {
    var entity = jpaRepository.save(UserMapper.map(user));
    return UserMapper.map(entity);
  }

  @Override
  public User update(User user) {
    var entity = jpaRepository
      .findById(user.id())
      .map(e -> UserMapper.merge(e, user))
      .get();
    return UserMapper.map(jpaRepository.save(entity));
  }

  @Override
  public Optional<User> byId(UUID id) {
    return jpaRepository.findById(id).map(UserMapper::map);
  }

  @Override
  public Optional<User> byEmailOrUsername(String emailOrUsername) {
    return jpaRepository.findByEmailOrUsername(emailOrUsername).map(UserMapper::map);
  }

  @Override
  public Boolean existsById(UUID id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public Boolean existsByEmailPhoneOrUsername(String email, String username, String phone) {
    return jpaRepository.existsByEmailOrUsernameOrMobilePhone(email, username, phone);
  }

  @Override
  public Collection<User> list() {
    return jpaRepository.findAll().stream().map(UserMapper::map).toList();
  }

  @Override
  public void deleteById(UUID id) {
    jpaRepository.deleteById(id);
  }

}
