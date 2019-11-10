package com.ntouzidis.demo.module.common;

import com.ntouzidis.demo.module.user.entity.Authority;
import com.ntouzidis.demo.module.user.entity.CustomUserDetails;
import com.ntouzidis.demo.module.user.entity.Tenant;
import com.ntouzidis.demo.module.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class Context {

  private final HttpServletRequest request;
  private final CustomUserDetails customUserDetails;

  public Context(HttpServletRequest request, CustomUserDetails customUserDetails) {
    this.request = request;
    this.customUserDetails = customUserDetails;
  }

  public CustomUserDetails getUserDetails() {
    return customUserDetails;
  }

  public User getUser() {
    return customUserDetails.getUser();
  }

  public Set<Authority> getAuthorities() {
    return customUserDetails.getAuthorities();
  }

  public Long getUserID() {
    return getUser().getId();
  }

  public Tenant getTenant() {
    return customUserDetails.getUser().getTenant();
  }

  public Long getTenantId() {
    return getTenant().getId();
  }

  public String getAdrress() {
    return request.getRemoteAddr();
  }
}


