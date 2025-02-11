package com.example.consoleApp.service;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.UserAcc;
import com.example.consoleApp.repository.AdminAccRepository;
import com.example.consoleApp.repository.UserAccRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccServiceImpl implements AccService {

    private final UserAccRepository userAccRepository;
    private final AdminAccRepository adminAccRepository;
    private final PasswordEncoder passwordEncoder;

    public AccServiceImpl(UserAccRepository userAccRepository, AdminAccRepository adminAccRepository, PasswordEncoder passwordEncoder) {
        this.userAccRepository = userAccRepository;
        this.adminAccRepository = adminAccRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserAcc addUser(UserAcc user) {

        UserAcc entity = new UserAcc();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        BeanUtils.copyProperties(user, entity);
        UserAcc savedUser = userAccRepository.save(entity);
        user.setPassword("******");
        user.setId(savedUser.getId());
        return user;
    }

    @Override
    public AdminAcc addAdmin(AdminAcc user) {

        AdminAcc entity = new AdminAcc();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        BeanUtils.copyProperties(user, entity);
        AdminAcc savedUser = adminAccRepository.save(entity);
        user.setPassword("******");
        user.setId(savedUser.getId());
        return user;
    }

    @Override
    public UserAcc getUserById(Long id) {

        return userAccRepository.findById(id).orElse(null);
    }

    @Override
    public AdminAcc getAdminById(Long id) {

        return adminAccRepository.findById(id).orElse(null);
    }

    @Override
    public boolean findUserByUsername(String username) {
        Optional<UserAcc> user = userAccRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public boolean findAdminByUsername(String username) {
        Optional<AdminAcc> admin = adminAccRepository.findByUsername(username);
        return admin.isPresent();
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserAcc UserByUsername = userAccRepository.findByUsername(username).orElse(null);
//        AdminAcc AdminByUsername = adminAccRepository.findByUsername(username).orElse(null);
//
//        if(UserByUsername != null) {
//            return new org.springframework.security.core.userdetails.User(
//                    UserByUsername.getUsername(),
//                    UserByUsername.getPassword(),
//                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
//            );
//
//        } else if(AdminByUsername != null){
//            return new org.springframework.security.core.userdetails.User(
//                    AdminByUsername.getUsername(),
//                    AdminByUsername.getPassword(),
//                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
//            );
//
//        } else {
//            throw new UsernameNotFoundException("User or Admin not found");
//        }
//
//
//
//    }
}
