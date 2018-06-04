package com.enterprisesys.plm.service;

import com.enterprisesys.plm.model.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}