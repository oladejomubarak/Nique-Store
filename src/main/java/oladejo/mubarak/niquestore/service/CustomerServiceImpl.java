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



}
