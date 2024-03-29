package com.ntouzidis.demo.module.auth_client.repository;

import com.ntouzidis.demo.module.auth_client.entity.AuthGrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGrantTypeRepository extends JpaRepository<AuthGrantType, Integer> {

    List<AuthGrantType> findByClientId(Long authClientId);
}
