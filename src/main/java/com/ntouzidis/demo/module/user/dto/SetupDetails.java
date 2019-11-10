package com.ntouzidis.demo.module.user.dto;

import com.ntouzidis.demo.module.user.entity.Tenant;
import com.ntouzidis.demo.module.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.provider.ClientDetails;

@Getter
@Setter
public class SetupDetails {

  private ClientDetails clientDetails;

  private Tenant tenant;

  private User user;
}
