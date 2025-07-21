package com.example.consoleApp.service;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.UserAcc;
import org.apache.catalina.User;

import java.util.Optional;

public interface AccService {

    UserAcc addUser(UserAcc user);
    AdminAcc addAdmin(AdminAcc user);
    UserAcc getUserById(Long id);
    AdminAcc getAdminById(Long id);


    UserAcc findUserByUsername(String username);

    AdminAcc findAdminByUsername(String username);

    Long getCurrentUserId();
}
