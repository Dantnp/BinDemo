package com.ntouzidis.demo.module.user.repository;

import com.ntouzidis.demo.module.user.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {

  Optional<Tenant> findByName(String username);
}
