package com.ntouzidis.demo.module.user.service;

import com.ntouzidis.demo.module.user.entity.Tenant;
import com.ntouzidis.demo.module.user.entity.User;

import java.util.List;

public interface IUserService {

  User getOne(Long id, String username);

  User getOne(Long id);

  User getOneByUsername(String username);

  List<User> getAll();

  User getOneGlobal(String username);

  User update(User user);

  User changePassword(Long id, String newPass, String confirmPass);

  void delete(User user);

  User create(String username, String email, String pass);

  User createTrader(String username, String email, String pass);

  User createAdmin(Tenant tenant, String username, String email, String pass);
}
