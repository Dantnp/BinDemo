package com.ntouzidis.demo.module.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Table(name = "users") // used 'users' instead of 'user' because of spring security default queries. leave it!!
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Setter
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="tenant_id")
  private Tenant tenant;

  @Setter
  @Column(name="username")
  private String username;

  @Setter
  @Column(name="password")
  private String password;

  @Setter
  @NotNull(message=" is required")
  @Pattern(
      regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      message="Invalid email"
  )
  @Column(name="email")
  private String email;

  @Setter
  @Column(name="enabled")
  private Boolean enabled;

  @Setter
  @Column(name="create_date")
  private LocalDate createDate;

  @JsonIgnore
  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER
  )
  private Set<Authority> authorities = new HashSet<>();

  public User() {
  }

  public User(String username, String password) {
    this(username, password, true, true, true, true);
  }

  public User(String username, String password, boolean enabled,
              boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {

    if (((username == null) || "".equals(username)) || (password == null))
      throw new IllegalArgumentException("Cannot pass null or empty values to constructor");

    this.username = username;
    this.password = password;
  }

  public void addAuthority(Authority authority) {
    authorities.add(authority);
    authority.setUser(this);
  }

  public void removeAuthority(Authority authority) {
    authorities.remove(authority);
    authority.setUser(null);
  }

}
