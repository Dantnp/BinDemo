package com.ntouzidis.demo.configuration;

import com.ntouzidis.demo.module.common.Context;
import com.ntouzidis.demo.module.user.entity.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class ContextProvider {

  private final HttpServletRequest request;

  public ContextProvider(HttpServletRequest request) {
    this.request = request;
  }

  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public Context context() {
    return new Context(request, (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
  }
}
