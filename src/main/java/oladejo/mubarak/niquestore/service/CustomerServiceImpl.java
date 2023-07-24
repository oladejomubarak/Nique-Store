package oladejo.mubarak.niquestore.service;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.config.security.JwtService;
import oladejo.mubarak.niquestore.data.dto.request.LoginRequest;
import oladejo.mubarak.niquestore.data.dto.request.UserDto;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Role;
import oladejo.mubarak.niquestore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecuredUserService securedUserService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    public AppUser findByEmail(String email){
        return userRepo.findUserByEmailIgnoreCase(email).orElseThrow(()-> new IllegalStateException("user not found"));
    }
    public AppUser register(UserDto userDto){
        boolean foundUser= userRepo.existsUserByEmailIgnoreCase(userDto.getEmail());
        if(foundUser){
            throw new IllegalStateException("email taken");
        }
        AppUser user = new AppUser();
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(Role.CUSTOMER);
        user.setEmail(userDto.getEmail());
        user.setRole(rolesSet);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);
        return user;
    }
    public String login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = securedUserService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        return "Bearer "+ token;

    }
    public List<AppUser> getAllUsers(){
        return userRepo.findAll();
    }

}
