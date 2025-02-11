package com.example.consoleApp.service;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.UserAcc;

public interface AccService {

    UserAcc addUser(UserAcc user);
    AdminAcc addAdmin(AdminAcc user);
    UserAcc getUserById(Long id);
    AdminAcc getAdminById(Long id);


    boolean findUserByUsername(String username);

    boolean findAdminByUsername(String username);
}
