package com.ntouzidis.demo.configuration;

import com.ntouzidis.demo.module.auth_client.service.AuthClientDetailsService;
import com.ntouzidis.demo.module.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private AuthClientDetailsService clientDetailsService;

  private final DataSource dataSource;

  public AuthorizationServerConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(tokenStore())
        .tokenServices(tokenServices())
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setTokenStore(this.tokenStore());
    return tokenServices;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }
}

