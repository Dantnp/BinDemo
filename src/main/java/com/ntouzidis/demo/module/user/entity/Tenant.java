package com.ntouzidis.demo.module.user.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tenant")
public class Tenant implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "create_date")
  private LocalDate createDate;

  public Tenant() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDate create_date) {
    this.createDate = create_date;
  }
}
