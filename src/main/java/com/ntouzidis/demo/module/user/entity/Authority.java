package com.ntouzidis.demo.module.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority, Serializable {

  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  // this field, although is redundant,
  // is kept for oauth related action. don't remove
  @Column(name = "username")
  private String username;

  @Getter
  @Setter
  @Column(name = "authority")
  private String authority;

  @Getter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Authority() {}

  public Authority(String authority) {
    this.authority = authority;
  }

  public void setUser(User user) {
    this.user = user;
    this.username = user.getUsername();
  }
}
