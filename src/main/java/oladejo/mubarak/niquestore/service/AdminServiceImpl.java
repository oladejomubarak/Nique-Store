package oladejo.mubarak.niquestore.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.CreateVendorRequest;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Role;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    private final UserRepo userRepo;
    @PostConstruct
    public void saveUser(){
        if (userRepo.findAll().size() == 0){
            AppUser user = new AppUser();
            Set<Role> rolesSet = new HashSet<>();
            rolesSet.add(Role.ADMIN);
            rolesSet.add(Role.CUSTOMER);
            user.setEmail("admin@gmail.com");
            user.setEnabled(true);
            user.setRole(rolesSet);
            user.setPassword(passwordEncoder.encode("12345"));
            userRepo.save(user);
        }
    }

    @Override
    public void registerVendor(CreateVendorRequest createVendorRequest) {
        AppUser foundUser = userService.findByEmail(createVendorRequest.getVendorEmail());
        if(foundUser.getRole().contains(Role.VENDOR)){ throw new NiqueStoreException("The user is already a vendor");
        }
        foundUser.getRole().add(Role.VENDOR);
        userService.saveUser(foundUser);
    }

    @Override
    public void removeVendor(CreateVendorRequest  createVendorRequest) {
        AppUser foundUser = userService.findByEmail(createVendorRequest.getVendorEmail());
        if(!foundUser.getRole().contains(Role.VENDOR)){ throw new NiqueStoreException("The user is not a vendor");
        }
        foundUser.getRole().remove(Role.VENDOR);
        userService.saveUser(foundUser);
    }

    @Override
    public List<AppUser> getAllUsers(){
        return userRepo.findAll();
    }
}
