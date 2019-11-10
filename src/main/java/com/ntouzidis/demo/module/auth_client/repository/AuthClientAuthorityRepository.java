package com.ntouzidis.demo.module.auth_client.repository;


import com.ntouzidis.demo.module.auth_client.entity.AuthClientAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthClientAuthorityRepository extends JpaRepository<AuthClientAuthority, Integer> {

    List<AuthClientAuthority> findByClientId(Long clientId);
}
