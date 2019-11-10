package com.ntouzidis.demo.module.user.repository;

import com.ntouzidis.demo.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

  List<User> findAllByTenantId(Long tenantId);

  Optional<User> findByTenantIdAndId(Long tenantId, Long id);

  Optional<User> findByTenantIdAndUsername(Long tenantId, String username);

  Optional<User> findByUsername(String username);

  void delete(User user);

}
