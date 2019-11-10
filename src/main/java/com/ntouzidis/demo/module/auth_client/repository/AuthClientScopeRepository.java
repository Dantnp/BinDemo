package com.ntouzidis.demo.module.auth_client.repository;

import com.ntouzidis.demo.module.auth_client.entity.AuthClientScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthClientScopeRepository extends JpaRepository<AuthClientScope, Integer> {

    List<AuthClientScope> findByClientId(Long clientId);
}
