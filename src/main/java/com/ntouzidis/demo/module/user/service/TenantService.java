package com.ntouzidis.demo.module.user.service;

import com.ntouzidis.demo.module.common.exceptions.NotFoundException;
import com.ntouzidis.demo.module.user.entity.Tenant;
import com.ntouzidis.demo.module.user.repository.TenantRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TenantService {

  private static final String NOT_FOUND = "Tenant not found";

  private final TenantRepository tenantRepository;

  public TenantService(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  public Tenant getOne(Integer id) {
    return tenantRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND));
  }

  public Tenant getOne(String name) {
    return tenantRepository.findByName(name).orElseThrow(() -> new NotFoundException(NOT_FOUND));
  }

  public List<Tenant> getAll() {
    return tenantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
  }

  @Transactional
  public Tenant create(String name) {
    Tenant tenant = new Tenant();
    tenant.setName(name);
    tenant.setCreateDate(LocalDate.now());
    return tenantRepository.save(tenant);
  }
}
