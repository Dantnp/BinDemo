package com.ntouzidis.demo.module.user.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.ntouzidis.demo.module.common.Context;
import com.ntouzidis.demo.module.common.exceptions.NotFoundException;
import com.ntouzidis.demo.module.user.entity.Authority;
import com.ntouzidis.demo.module.user.entity.Tenant;
import com.ntouzidis.demo.module.user.entity.User;
import com.ntouzidis.demo.module.user.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.ntouzidis.demo.module.common.constants.RoleConstants.ROLE_ADMIN;
import static com.ntouzidis.demo.module.common.constants.RoleConstants.ROLE_USER;

@Service
public class UserService implements IUserService {

  private final Context context;
  private final IUserRepository userRepository;

  public UserService(Context context, IUserRepository userRepository) {
    this.context = context;
    this.userRepository = userRepository;
  }

  @Override
  public User getOne(Long id, String username) {
    return null;
  }

  @Override
  public User getOne(Long id) {
    return userRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public User getOneByUsername(String username) {
    return null;
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User getOneGlobal(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public User update(User user) {
    return null;
  }

  @Override
  public User changePassword(Long id, String newPass, String confirmPass) {
    return null;
  }

  @Override
  public void delete(User user) {

  }

  @Override
  public User create(String username, String email, String pass) {
    return createUser(username, pass, email, context.getTenant(), ImmutableSet.of(new Authority(ROLE_USER)));
  }

  @Override
  public User createTrader(String username, String email, String pass) {
    return null;
  }

  @Override
  public User createAdmin(Tenant tenant, String username, String email, String pass) {
    return createUser(username, pass, email, tenant, ImmutableSet.of(new Authority(ROLE_ADMIN)));
  }

  private User createUser(String username, String pass, String email, Tenant tenant,  Set<Authority> authorities) {
    Preconditions.checkArgument(!usernameExistsGlobally(username), "username exists");

    User user = new User(username, new BCryptPasswordEncoder().encode(pass));
    user.setTenant(tenant);
    user.setEmail(email);
    user.setEnabled(false);
    user.setCreateDate(LocalDate.now());

    authorities.forEach(user::addAuthority);

    return userRepository.save(user);
  }

  private boolean usernameExistsGlobally(String username) {
    return userRepository.findByUsername(username).isPresent();
  }
}
