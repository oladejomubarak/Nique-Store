package oladejo.mubarak.niquestore.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Role;
import oladejo.mubarak.niquestore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @PostConstruct
    public void saveUser(){
        if (userRepo.findAll().size() == 0){
            AppUser user = new AppUser();
            Set<Role> rolesSet = new HashSet<>();
            rolesSet.add(Role.ADMIN);
            rolesSet.add(Role.CUSTOMER);
            user.setEmail("admin@gmail.com");
            user.setRole(rolesSet);
            user.setPassword(passwordEncoder.encode("1234"));
            userRepo.save(user);
        }
    }

    @Override
    public void registerVendor(String email) {

    }
}
